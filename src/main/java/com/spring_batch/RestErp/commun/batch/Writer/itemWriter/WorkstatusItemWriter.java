package com.spring_batch.RestErp.commun.batch.Writer.itemWriter;

import com.spring_batch.RestErp.commun.dto.dim.DimWorkstatus;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class WorkstatusItemWriter implements ItemWriter<DimWorkstatus> {

    private final JdbcTemplate jdbcTemplate;

    /*
     * Cache en mémoire :
     * key   = company_id
     * value = company_key
     */
    private final Map<Long, Integer> companyKeyCache = new HashMap<>();

    public WorkstatusItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends DimWorkstatus> chunk) throws Exception {
        for (DimWorkstatus item : chunk) {

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
                UPDATE dim_workstatus
                SET effective_to = ?,
                    is_current = FALSE
                WHERE company_id = ?
                  AND workstatus_id = ?
                  AND is_current = TRUE
                  AND (
                      COALESCE(company_key, -1) <> COALESCE(?, -1)
                      OR COALESCE(status_label, '') <> COALESCE(?, '')
                      OR COALESCE(position, -1) <> COALESCE(?, -1)
                      OR COALESCE(archive, FALSE) <> COALESCE(?, FALSE)
                  )
            """,
                    effectiveFrom,
                    item.getCompanyId(),
                    item.getWorkstatusId(),
                    item.getCompanyKey(),
                    item.getStatusLabel(),
                    item.getPosition(),
                    item.getArchive()
            );

            /*
             * 2) Insérer une nouvelle ligne seulement si aucune ligne courante identique n'existe
             */
            jdbcTemplate.update("""
                INSERT INTO dim_workstatus (
                    workstatus_id,
                    company_id,
                    company_key,
                    status_label,
                    position,
                    archive,
                    effective_from,
                    effective_to,
                    is_current
                )
                SELECT ?, ?, ?, ?, ?, ?, ?, NULL, TRUE
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM dim_workstatus
                    WHERE company_id = ?
                      AND workstatus_id = ?
                      AND is_current = TRUE
                      AND COALESCE(company_key, -1) = COALESCE(?, -1)
                      AND COALESCE(status_label, '') = COALESCE(?, '')
                      AND COALESCE(position, -1) = COALESCE(?, -1)
                      AND COALESCE(archive, FALSE) = COALESCE(?, FALSE)
                )
            """,
                    item.getWorkstatusId(),
                    item.getCompanyId(),
                    item.getCompanyKey(),
                    item.getStatusLabel(),
                    item.getPosition(),
                    item.getArchive(),
                    effectiveFrom,

                    item.getCompanyId(),
                    item.getWorkstatusId(),
                    item.getCompanyKey(),
                    item.getStatusLabel(),
                    item.getPosition(),
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