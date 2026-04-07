package com.spring_batch.RestErp.finance.batch.writer.item;

import com.spring_batch.RestErp.finance.dto.dim.DimVendor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class VendorItemWriter implements ItemWriter<DimVendor> {

    private final JdbcTemplate jdbcTemplate;

    /*
     * Cache en mémoire :
     * key   = company_id
     * value = company_key
     */
    private final Map<Long, Integer> companyKeyCache = new HashMap<>();

    public VendorItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends DimVendor> chunk) throws Exception {
        for (DimVendor item : chunk) {

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
                UPDATE dim_vendor
                SET effective_to = ?,
                    is_current = FALSE
                WHERE company_id = ?
                  AND vendor_id = ?
                  AND is_current = TRUE
                  AND (
                      COALESCE(company_key, -1) <> COALESCE(?, -1)
                      OR COALESCE(vendor_name, '') <> COALESCE(?, '')
                      OR COALESCE(industry, '') <> COALESCE(?, '')
                    )
            """,
                    effectiveFrom,
                    item.getCompanyId(),
                    item.getVendorId(),
                    item.getCompanyKey(),
                    item.getVendorName(),
                    item.getIndustry()
            );

            /*
             * 2) Insérer une nouvelle ligne seulement si aucune ligne courante identique n'existe
             */
            jdbcTemplate.update("""
                INSERT INTO dim_vendor (
                    vendor_id,
                    company_id,
                    company_key,
                    vendor_name,
                    industry,
                    effective_from,
                    effective_to,
                    is_current
                )
                SELECT ?, ?, ?, ?, ?, ?, NULL, TRUE
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM dim_vendor
                    WHERE company_id = ?
                      AND vendor_id = ?
                      AND is_current = TRUE
                      AND COALESCE(company_key, -1) = COALESCE(?, -1)
                      AND COALESCE(vendor_name, '') = COALESCE(?, '')
                      AND COALESCE(industry, '') = COALESCE(?, '')
                )
            """,
                    item.getVendorId(),
                    item.getCompanyId(),
                    item.getCompanyKey(),
                    item.getVendorName(),
                    item.getIndustry(),
                    effectiveFrom,

                    item.getCompanyId(),
                    item.getVendorId(),
                    item.getCompanyKey(),
                    item.getVendorName(),
                    item.getIndustry()
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
