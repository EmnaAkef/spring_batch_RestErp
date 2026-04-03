package com.spring_batch.RestErp.commun.batch.Writer.itemWriter;

import com.spring_batch.RestErp.commun.dto.dim.DimCustomer;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class CustomerItemWriter implements ItemWriter<DimCustomer> {

    private final JdbcTemplate jdbcTemplate;

    /*
     * Cache en mémoire :
     * key   = company_id
     * value = company_key
     */
    private final Map<Long, Integer> companyKeyCache = new HashMap<>();

    public CustomerItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends DimCustomer> chunk) throws Exception {
        for (DimCustomer item : chunk) {

            // 1) Récupérer le company_key depuis le cache ou dim_company
            Integer companyKey = getCompanyKey(item.getCompanyId());

            if (companyKey == null) {
                throw new IllegalStateException(
                        "Aucune company courante trouvée dans dim_company pour company_id = " + item.getCompanyId()
                );
            }

            item.setCompanyKey(companyKey);

            Timestamp effectiveFrom = Timestamp.valueOf(item.getEffectiveFrom());

            /*
             * 2) Fermer la ligne courante si au moins un attribut a changé
             */
            jdbcTemplate.update("""
                UPDATE dim_customer
                SET effective_to = ?,
                    is_current = FALSE
                WHERE company_id = ?
                  AND customer_id = ?
                  AND is_current = TRUE
                  AND (
                      COALESCE(company_key, -1) <> COALESCE(?, -1)
                      OR COALESCE(client_category, '') <> COALESCE(?, '')
                      OR COALESCE(type, '') <> COALESCE(?, '')
                      OR COALESCE(status, '') <> COALESCE(?, '')
                      OR COALESCE(active, FALSE) <> COALESCE(?, FALSE)
                      OR COALESCE(city, '') <> COALESCE(?, '')
                      OR COALESCE(nationality, '') <> COALESCE(?, '')
                      OR COALESCE(contact_name, '') <> COALESCE(?, '')
                      OR COALESCE(organization_name, '') <> COALESCE(?, '')
                  )
            """,
                    effectiveFrom,
                    item.getCompanyId(),
                    item.getCustomerId(),
                    item.getCompanyKey(),
                    item.getClientCategory(),
                    item.getType(),
                    item.getStatus(),
                    item.getActive(),
                    item.getCity(),
                    item.getNationality(),
                    item.getContactName(),
                    item.getOrganizationName()
            );

            /*
             * 3) Insérer une nouvelle ligne seulement si aucune ligne courante identique n'existe
             */
            jdbcTemplate.update("""
                INSERT INTO dim_customer (
                    customer_id,
                    company_id,
                    company_key,
                    client_category,
                    type,
                    status,
                    active,
                    city,
                    nationality,
                    contact_name,
                    organization_name,
                    effective_from,
                    effective_to,
                    is_current
                )
                SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, TRUE
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM dim_customer
                    WHERE company_id = ?
                      AND customer_id = ?
                      AND is_current = TRUE
                      AND COALESCE(company_key, -1) = COALESCE(?, -1)
                      AND COALESCE(client_category, '') = COALESCE(?, '')
                      AND COALESCE(type, '') = COALESCE(?, '')
                      AND COALESCE(status, '') = COALESCE(?, '')
                      AND COALESCE(active, FALSE) = COALESCE(?, FALSE)
                      AND COALESCE(city, '') = COALESCE(?, '')
                      AND COALESCE(nationality, '') = COALESCE(?, '')
                      AND COALESCE(contact_name, '') = COALESCE(?, '')
                      AND COALESCE(organization_name, '') = COALESCE(?, '')
                )
            """,
                    item.getCustomerId(),
                    item.getCompanyId(),
                    item.getCompanyKey(),
                    item.getClientCategory(),
                    item.getType(),
                    item.getStatus(),
                    item.getActive(),
                    item.getCity(),
                    item.getNationality(),
                    item.getContactName(),
                    item.getOrganizationName(),
                    effectiveFrom,

                    item.getCompanyId(),
                    item.getCustomerId(),
                    item.getCompanyKey(),
                    item.getClientCategory(),
                    item.getType(),
                    item.getStatus(),
                    item.getActive(),
                    item.getCity(),
                    item.getNationality(),
                    item.getContactName(),
                    item.getOrganizationName()
            );
        }
    }

    /*
     * Méthode utilitaire :
     * - si company_id déjà vu, retourne company_key depuis le cache
     * - sinon, va le chercher dans dim_company puis le stocke
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