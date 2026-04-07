package com.spring_batch.RestErp.finance.batch.writer.item;

import com.spring_batch.RestErp.finance.dto.dim.DimChartAccount;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class ChartAccountItemWriter implements ItemWriter<DimChartAccount> {

    private final JdbcTemplate jdbcTemplate;

    /*
     * Cache en mémoire :
     * key   = company_id
     * value = company_key
     */
    private final Map<Long, Integer> companyKeyCache = new HashMap<>();

    public ChartAccountItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends DimChartAccount> chunk) throws Exception {
        for (DimChartAccount item : chunk) {

            Integer companyKey = getCompanyKey(item.getCompanyId());

            if (companyKey == null) {
                throw new IllegalStateException(
                        "Aucune company courante trouvée dans dim_company pour company_id = " + item.getCompanyId()
                );
            }

            item.setCompanyKey(companyKey);

            Timestamp effectiveFrom = Timestamp.valueOf(item.getEffectiveFrom());

            /*
             * 1) Fermer la ligne courante si un attribut a changé
             */
            jdbcTemplate.update("""
                UPDATE dim_chart_account
                SET effective_to = ?,
                    is_current = FALSE
                WHERE company_id = ?
                  AND chart_id = ?
                  AND is_current = TRUE
                  AND (
                      COALESCE(company_key, -1) <> COALESCE(?, -1)
                      OR COALESCE(account_name, '') <> COALESCE(?, '')
                      OR COALESCE(transaction_type, '') <> COALESCE(?, '')
                      OR COALESCE(account_type, '') <> COALESCE(?, '')
                  )
            """,
                    effectiveFrom,
                    item.getCompanyId(),
                    item.getChartId(),
                    item.getCompanyKey(),
                    item.getAccountName(),
                    item.getTransactionType(),
                    item.getAccountType()
            );

            /*
             * 2) Insérer une nouvelle ligne seulement si aucune ligne courante identique n'existe
             */
            jdbcTemplate.update("""
                INSERT INTO dim_chart_account (
                    chart_id,
                    company_id,
                    company_key,
                    account_name,
                    transaction_type,
                    account_type,
                    effective_from,
                    effective_to,
                    is_current
                )
                SELECT ?, ?, ?, ?, ?, ?, ?, NULL, TRUE
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM dim_chart_account
                    WHERE company_id = ?
                      AND chart_id = ?
                      AND is_current = TRUE
                      AND COALESCE(company_key, -1) = COALESCE(?, -1)
                      AND COALESCE(account_name, '') = COALESCE(?, '')
                      AND COALESCE(transaction_type, '') = COALESCE(?, '')
                      AND COALESCE(account_type, '') = COALESCE(?, '')
                )
            """,
                    item.getChartId(),
                    item.getCompanyId(),
                    item.getCompanyKey(),
                    item.getAccountName(),
                    item.getTransactionType(),
                    item.getAccountType(),
                    effectiveFrom,

                    item.getCompanyId(),
                    item.getChartId(),
                    item.getCompanyKey(),
                    item.getAccountName(),
                    item.getTransactionType(),
                    item.getAccountType()
            );
        }
    }

    /*
     * Récupérer company_key avec cache
     */
    private Integer getCompanyKey(Long companyId) {
        if (companyId == null) {
            return null;
        }

        if (companyKeyCache.containsKey(companyId)) {
            return companyKeyCache.get(companyId);
        }

        Integer companyKey = jdbcTemplate.query("""
            SELECT company_key
            FROM dim_company
            WHERE company_id = ?
              AND is_current = TRUE
            ORDER BY company_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("company_key") : null, companyId);

        if (companyKey != null) {
            companyKeyCache.put(companyId, companyKey);
        }

        return companyKey;
    }
}
