package com.spring_batch.RestErp.sales.batch.writer.itemWriter;


import com.spring_batch.RestErp.sales.dto.dim.DimProduct;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class ProductItemWriter implements ItemWriter<DimProduct> {

    private final JdbcTemplate jdbcTemplate;

    /*
     * Cache en mémoire :
     * key   = company_id
     * value = company_key
     */
    private final Map<Long, Integer> companyKeyCache = new HashMap<>();

    public ProductItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends DimProduct> chunk) throws Exception {
        for (DimProduct item : chunk) {

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
                UPDATE dim_product
                SET effective_to = ?,
                    is_current = FALSE
                WHERE company_id = ?
                  AND product_id = ?
                  AND is_current = TRUE
                  AND (
                      COALESCE(company_key, -1) <> COALESCE(?, -1)
                      OR COALESCE(product_name, '') <> COALESCE(?, '')
                      OR COALESCE(category, '') <> COALESCE(?, '')
                      OR COALESCE(product_type, '') <> COALESCE(?, '')
                      OR COALESCE(status, '') <> COALESCE(?, '')
                      OR COALESCE(uom, '') <> COALESCE(?, '')
                      OR COALESCE(archive, FALSE) <> COALESCE(?, FALSE)
                  )
            """,
                    effectiveFrom,
                    item.getCompanyId(),
                    item.getProductId(),
                    item.getCompanyKey(),
                    item.getProductName(),
                    item.getCategory(),
                    item.getProductType(),
                    item.getStatus(),
                    item.getUom(),
                    item.getArchive()
            );

            /*
             * 2) Insérer une nouvelle ligne seulement si aucune ligne courante identique n'existe
             */
            jdbcTemplate.update("""
                INSERT INTO dim_product (
                    product_id,
                    company_id,
                    company_key,
                    product_name,
                    category,
                    product_type,
                    status,
                    uom,
                    archive,
                    effective_from,
                    effective_to,
                    is_current
                )
                SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, TRUE
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM dim_product
                    WHERE company_id = ?
                      AND product_id = ?
                      AND is_current = TRUE
                      AND COALESCE(company_key, -1) = COALESCE(?, -1)
                      AND COALESCE(product_name, '') = COALESCE(?, '')
                      AND COALESCE(category, '') = COALESCE(?, '')
                      AND COALESCE(product_type, '') = COALESCE(?, '')
                      AND COALESCE(status, '') = COALESCE(?, '')
                      AND COALESCE(uom, '') = COALESCE(?, '')
                      AND COALESCE(archive, FALSE) = COALESCE(?, FALSE)
                )
            """,
                    item.getProductId(),
                    item.getCompanyId(),
                    item.getCompanyKey(),
                    item.getProductName(),
                    item.getCategory(),
                    item.getProductType(),
                    item.getStatus(),
                    item.getUom(),
                    item.getArchive(),
                    effectiveFrom,

                    item.getCompanyId(),
                    item.getProductId(),
                    item.getCompanyKey(),
                    item.getProductName(),
                    item.getCategory(),
                    item.getProductType(),
                    item.getStatus(),
                    item.getUom(),
                    item.getArchive()
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