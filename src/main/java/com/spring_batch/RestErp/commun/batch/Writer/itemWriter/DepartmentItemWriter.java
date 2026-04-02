package com.spring_batch.RestErp.commun.batch.Writer.itemWriter;

import com.spring_batch.RestErp.commun.dto.dim.DimDepartment;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class DepartmentItemWriter implements ItemWriter<DimDepartment> {

    private final JdbcTemplate jdbcTemplate;

    /*
     * Cache en mémoire :
     * key   = company_id
     * value = company_key
     */
    private final Map<Long, Integer> companyKeyCache = new HashMap<>();

    public DepartmentItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends DimDepartment> chunk) throws Exception {
        for (DimDepartment item : chunk) {

            // 1) Récupérer le company_key depuis le cache ou depuis dim_company
            Integer companyKey = getCompanyKey(item.getCompanyId());

            if (companyKey == null) {
                throw new IllegalStateException(
                        "Aucune company courante trouvée dans dim_company pour company_id = " + item.getCompanyId()
                );
            }

            // 2) Injecter la clé technique dans l'objet cible
            item.setCompanyKey(companyKey);

            Timestamp effectiveFrom = Timestamp.valueOf(item.getEffectiveFrom());

            /*
             * 3) Fermer la ligne courante si un attribut a changé
             */
            jdbcTemplate.update("""
                UPDATE dim_department
                SET effective_to = ?,
                    is_current = FALSE
                WHERE company_id = ?
                  AND department_id = ?
                  AND is_current = TRUE
                  AND (
                      COALESCE(company_key, -1) <> COALESCE(?, -1)
                      OR COALESCE(department_name, '') <> COALESCE(?, '')
                      OR COALESCE(supervisor_id, -1) <> COALESCE(?, -1)
                      OR COALESCE(supervisor_name, '') <> COALESCE(?, '')
                      OR COALESCE(active, FALSE) <> COALESCE(?, FALSE)
                  )
            """,
                    effectiveFrom,
                    item.getCompanyId(),
                    item.getDepartmentId(),
                    item.getCompanyKey(),
                    item.getDepartmentName(),
                    item.getSupervisorId(),
                    item.getSupervisorName(),
                    item.getActive()
            );

            /*
             * 4) Insérer une nouvelle ligne seulement si aucune ligne courante identique n'existe
             */
            jdbcTemplate.update("""
                INSERT INTO dim_department (
                    department_id,
                    company_id,
                    company_key,
                    department_name,
                    supervisor_id,
                    supervisor_name,
                    active,
                    effective_from,
                    effective_to,
                    is_current
                )
                SELECT ?, ?, ?, ?, ?, ?, ?, ?, NULL, TRUE
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM dim_department
                    WHERE company_id = ?
                      AND department_id = ?
                      AND is_current = TRUE
                      AND COALESCE(company_key, -1) = COALESCE(?, -1)
                      AND COALESCE(department_name, '') = COALESCE(?, '')
                      AND COALESCE(supervisor_id, -1) = COALESCE(?, -1)
                      AND COALESCE(supervisor_name, '') = COALESCE(?, '')
                      AND COALESCE(active, FALSE) = COALESCE(?, FALSE)
                )
            """,
                    item.getDepartmentId(),
                    item.getCompanyId(),
                    item.getCompanyKey(),
                    item.getDepartmentName(),
                    item.getSupervisorId(),
                    item.getSupervisorName(),
                    item.getActive(),
                    effectiveFrom,

                    item.getCompanyId(),
                    item.getDepartmentId(),
                    item.getCompanyKey(),
                    item.getDepartmentName(),
                    item.getSupervisorId(),
                    item.getSupervisorName(),
                    item.getActive()
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