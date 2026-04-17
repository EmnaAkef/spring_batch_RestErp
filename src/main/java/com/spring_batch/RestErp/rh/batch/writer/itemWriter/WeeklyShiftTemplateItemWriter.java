package com.spring_batch.RestErp.rh.batch.writer.itemWriter;

import com.spring_batch.RestErp.rh.dto.dim.DimWeeklyShiftTemplate;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class WeeklyShiftTemplateItemWriter implements ItemWriter<DimWeeklyShiftTemplate> {

    private final JdbcTemplate jdbcTemplate;
    private final Map<Long, Integer> companyKeyCache = new HashMap<>();

    public WeeklyShiftTemplateItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends DimWeeklyShiftTemplate> chunk) throws Exception {
        for (DimWeeklyShiftTemplate item : chunk) {

            Integer companyKey = getCompanyKey(item.getCompanyId());

            if (companyKey == null) {
                throw new IllegalStateException(
                        "Aucune company courante trouvée dans dim_company pour company_id = " + item.getCompanyId()
                );
            }

            item.setCompanyKey(companyKey);

            Timestamp effectiveFrom = Timestamp.valueOf(item.getEffectiveFrom());

            jdbcTemplate.update("""
                UPDATE dim_weekly_shift_template
                SET effective_to = ?,
                    is_current = FALSE
                WHERE company_id = ?
                  AND weekly_template_id = ?
                  AND is_current = TRUE
                  AND (
                      COALESCE(company_key, -1) <> COALESCE(?, -1)
                      OR COALESCE(template_name, '') <> COALESCE(?, '')
                      OR COALESCE(type, '') <> COALESCE(?, '')
                      OR COALESCE(creator_id, -1) <> COALESCE(?, -1)
                      OR COALESCE(creator_name, '') <> COALESCE(?, '')
                      OR COALESCE(total_work_hours, -1) <> COALESCE(?, -1)
                  )
            """,
                    effectiveFrom,
                    item.getCompanyId(),
                    item.getWeeklyTemplateId(),
                    item.getCompanyKey(),
                    item.getTemplateName(),
                    item.getType(),
                    item.getCreatorId(),
                    item.getCreatorName(),
                    item.getTotalWorkHours()
            );

            jdbcTemplate.update("""
                INSERT INTO dim_weekly_shift_template (
                    weekly_template_id,
                    company_id,
                    company_key,
                    template_name,
                    type,
                    creator_id,
                    creator_name,
                    start_date,
                    end_date,
                    total_work_hours,
                    effective_from,
                    effective_to,
                    is_current
                )
                SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, TRUE
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM dim_weekly_shift_template
                    WHERE company_id = ?
                      AND weekly_template_id = ?
                      AND is_current = TRUE
                      AND COALESCE(company_key, -1) = COALESCE(?, -1)
                      AND COALESCE(template_name, '') = COALESCE(?, '')
                      AND COALESCE(type, '') = COALESCE(?, '')
                      AND COALESCE(creator_id, -1) = COALESCE(?, -1)
                      AND COALESCE(creator_name, '') = COALESCE(?, '')
                      AND COALESCE(total_work_hours, -1) = COALESCE(?, -1)
                )
            """,
                    item.getWeeklyTemplateId(),
                    item.getCompanyId(),
                    item.getCompanyKey(),
                    item.getTemplateName(),
                    item.getType(),
                    item.getCreatorId(),
                    item.getCreatorName(),
                    item.getStartDate(),
                    item.getEndDate(),
                    item.getTotalWorkHours(),
                    effectiveFrom,

                    item.getCompanyId(),
                    item.getWeeklyTemplateId(),
                    item.getCompanyKey(),
                    item.getTemplateName(),
                    item.getType(),
                    item.getCreatorId(),
                    item.getCreatorName(),
                    item.getTotalWorkHours()
            );
        }
    }

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