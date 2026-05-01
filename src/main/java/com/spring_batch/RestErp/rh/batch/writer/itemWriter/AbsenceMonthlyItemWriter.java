package com.spring_batch.RestErp.rh.batch.writer.itemWriter;

import com.spring_batch.RestErp.rh.dto.fact.FactAbsenceMonthly;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AbsenceMonthlyItemWriter implements ItemWriter<FactAbsenceMonthly> {

    private final JdbcTemplate jdbcTemplate;

    private final Map<LocalDate, Integer> dateKeyCache = new HashMap<>();
    private final Map<Long, Integer> companyKeyCache = new HashMap<>();
    private final Map<String, Integer> departmentKeyCache = new HashMap<>();

    public AbsenceMonthlyItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends FactAbsenceMonthly> chunk) {
        for (FactAbsenceMonthly item : chunk) {


            Integer monthDateKey = getDateKey(item.getMonthDate());
            item.setMonthDateKey(monthDateKey);

            Integer companyKey = getCompanyKey(item.getCompanyId());
            item.setCompanyKey(companyKey);

            Integer departmentKey = getDepartmentKey(item.getCompanyId(), item.getDepartmentId());
            item.setDepartmentKey(departmentKey);

            // sécurité : on ignore la ligne si une clé DW manque
            if (item.getMonthDateKey() == null
                    || item.getCompanyKey() == null
                    || item.getDepartmentKey() == null) {
                continue;
            }

            jdbcTemplate.update("""
                INSERT INTO fact_absence_monthly (
                    month_date_key,
                    company_key,
                    department_key,
                    scheduled_shifts_count,
                    absent_shifts_count,
                    absence_rate_pct
                )
                VALUES (?, ?, ?, ?, ?, ?)
                ON CONFLICT (month_date_key, company_key, department_key)
                DO UPDATE SET
                    scheduled_shifts_count = EXCLUDED.scheduled_shifts_count,
                    absent_shifts_count = EXCLUDED.absent_shifts_count,
                    absence_rate_pct = EXCLUDED.absence_rate_pct
            """,
                    item.getMonthDateKey(),
                    item.getCompanyKey(),
                    item.getDepartmentKey(),
                    item.getScheduledShiftsCount(),
                    item.getAbsentShiftsCount(),
                    item.getAbsenceRatePct()
            );
        }
    }

    private Integer getDateKey(LocalDate postingDate) {
        if (postingDate == null) {
            return null;
        }

        if (dateKeyCache.containsKey(postingDate)) {
            return dateKeyCache.get(postingDate);
        }

        Integer dateKey = jdbcTemplate.query("""
        SELECT date_key
        FROM dim_date
        WHERE full_date = ?
        LIMIT 1
    """, rs -> rs.next() ? rs.getInt("date_key") : null, postingDate);

        if (dateKey != null) {
            dateKeyCache.put(postingDate, dateKey);
        }

        return dateKey;
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

    private Integer getDepartmentKey(Long companyId, Long departmentId) {
        if (companyId == null || departmentId == null) {
            return null;
        }

        String cacheKey = companyId + "|" + departmentId;

        if (departmentKeyCache.containsKey(cacheKey)) {
            return departmentKeyCache.get(cacheKey);
        }

        Integer departmentKey = jdbcTemplate.query("""
            SELECT department_key
            FROM dim_department
            WHERE company_id = ?
              AND department_id = ?
              AND is_current = TRUE
            ORDER BY department_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("department_key") : null, companyId, departmentId);

        if (departmentKey != null) {
            departmentKeyCache.put(cacheKey, departmentKey);
        }

        return departmentKey;
    }
}