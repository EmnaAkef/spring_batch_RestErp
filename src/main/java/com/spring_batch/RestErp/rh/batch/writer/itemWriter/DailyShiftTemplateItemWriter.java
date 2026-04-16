package com.spring_batch.RestErp.rh.batch.writer.itemWriter;

import com.spring_batch.RestErp.rh.dto.dim.DimDailyShiftTemplate;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class DailyShiftTemplateItemWriter implements ItemWriter<DimDailyShiftTemplate> {

    private final JdbcTemplate jdbcTemplate;

    private final Map<Long, Integer> companyKeyCache = new HashMap<>();
    private final Map<String, Integer> weeklyTemplateKeyCache = new HashMap<>();

    public DailyShiftTemplateItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends DimDailyShiftTemplate> chunk) throws Exception {
        for (DimDailyShiftTemplate item : chunk) {

            Integer companyKey = getCompanyKey(item.getCompanyId());

            if (companyKey == null) {
                throw new IllegalStateException(
                        "Aucune company courante trouvée dans public.dim_company pour company_id = " + item.getCompanyId()
                );
            }

            item.setCompanyKey(companyKey);

            Integer weeklyTemplateKey = getWeeklyTemplateKey(item.getCompanyId(), item.getWeeklyTemplateId());
            item.setWeeklyTemplateKey(weeklyTemplateKey);

            Timestamp effectiveFrom = Timestamp.valueOf(item.getEffectiveFrom());


            jdbcTemplate.update("""
                UPDATE public.dim_daily_shift_template
                SET effective_to = ?,
                    is_current = FALSE
                WHERE company_id = ?
                  AND daily_shift_id = ?
                  AND is_current = TRUE
                  AND (
                      COALESCE(company_key, -1) <> COALESCE(?, -1)
                      OR COALESCE(weekly_template_id, -1) <> COALESCE(?, -1)
                      OR COALESCE(weekly_template_key, -1) <> COALESCE(?, -1)
                      OR COALESCE(day_of_week, -1) <> COALESCE(?, -1)
                      OR COALESCE(approve, FALSE) <> COALESCE(?, FALSE)
                      OR COALESCE(general_checkin_state, -1) <> COALESCE(?, -1)
                      OR COALESCE(general_checkout_state, -1) <> COALESCE(?, -1)
                      OR COALESCE(working_hours, -1) <> COALESCE(?, -1)
                  )
            """,
                    effectiveFrom,
                    item.getCompanyId(),
                    item.getDailyShiftId(),
                    item.getCompanyKey(),
                    item.getWeeklyTemplateId(),
                    item.getWeeklyTemplateKey(),
                    item.getDayOfWeek(),
                    item.getApprove(),
                    item.getGeneralCheckinState(),
                    item.getGeneralCheckoutState(),
                    item.getWorkingHours()
            );

            jdbcTemplate.update("""
                INSERT INTO public.dim_daily_shift_template (
                    daily_shift_id,
                    company_id,
                    company_key,
                    weekly_template_id,
                    weekly_template_key,
                    day_of_week,
                    approve,
                    general_checkin_state,
                    general_checkout_state,
                    working_hours,
                    effective_from,
                    effective_to,
                    is_current
                )
                SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, TRUE
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM public.dim_daily_shift_template
                    WHERE company_id = ?
                      AND daily_shift_id = ?
                      AND is_current = TRUE
                      AND COALESCE(company_key, -1) = COALESCE(?, -1)
                      AND COALESCE(weekly_template_id, -1) = COALESCE(?, -1)
                      AND COALESCE(weekly_template_key, -1) = COALESCE(?, -1)
                      AND COALESCE(day_of_week, -1) = COALESCE(?, -1)
                      AND COALESCE(approve, FALSE) = COALESCE(?, FALSE)
                      AND COALESCE(general_checkin_state, -1) = COALESCE(?, -1)
                      AND COALESCE(general_checkout_state, -1) = COALESCE(?, -1)
                      AND COALESCE(working_hours, -1) = COALESCE(?, -1)
                )
            """,
                    item.getDailyShiftId(),
                    item.getCompanyId(),
                    item.getCompanyKey(),
                    item.getWeeklyTemplateId(),
                    item.getWeeklyTemplateKey(),
                    item.getDayOfWeek(),
                    item.getApprove(),
                    item.getGeneralCheckinState(),
                    item.getGeneralCheckoutState(),
                    item.getWorkingHours(),
                    effectiveFrom,

                    item.getCompanyId(),
                    item.getDailyShiftId(),
                    item.getCompanyKey(),
                    item.getWeeklyTemplateId(),
                    item.getWeeklyTemplateKey(),
                    item.getDayOfWeek(),
                    item.getApprove(),
                    item.getGeneralCheckinState(),
                    item.getGeneralCheckoutState(),
                    item.getWorkingHours()
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
            FROM public.dim_company
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

    private Integer getWeeklyTemplateKey(Long companyId, Long weeklyTemplateId) {
        if (companyId == null || weeklyTemplateId == null) {
            return null;
        }

        String cacheKey = companyId + "_" + weeklyTemplateId;

        if (weeklyTemplateKeyCache.containsKey(cacheKey)) {
            return weeklyTemplateKeyCache.get(cacheKey);
        }

        Integer weeklyTemplateKey = jdbcTemplate.query("""
            SELECT weekly_template_key
            FROM public.dim_weekly_shift_template
            WHERE company_id = ?
              AND weekly_template_id = ?
              AND is_current = TRUE
            ORDER BY weekly_template_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("weekly_template_key") : null, companyId, weeklyTemplateId);

        if (weeklyTemplateKey != null) {
            weeklyTemplateKeyCache.put(cacheKey, weeklyTemplateKey);
        }

        return weeklyTemplateKey;
    }
}