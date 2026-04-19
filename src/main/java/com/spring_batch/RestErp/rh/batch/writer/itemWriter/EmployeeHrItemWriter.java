package com.spring_batch.RestErp.rh.batch.writer.itemWriter;

import com.spring_batch.RestErp.rh.dto.fact.FactEmployeeHr;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EmployeeHrItemWriter implements ItemWriter<FactEmployeeHr> {

    private final JdbcTemplate jdbcTemplate;

    private final Map<LocalDate, Integer> dateKeyCache = new HashMap<>();
    private final Map<String, Integer> userKeyCache = new HashMap<>();
    private final Map<Long, Integer> companyKeyCache = new HashMap<>();
    private final Map<String, Integer> departmentKeyCache = new HashMap<>();

    public EmployeeHrItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends FactEmployeeHr> chunk) throws Exception {
        for (FactEmployeeHr item : chunk) {

            Integer dateKey = getDateKey(item.getFactDate());
            item.setDateKey(dateKey);

            Integer userKey = getUserKey(item.getCompanyId(), item.getUserId());
            item.setUserKey(userKey);

            Integer companyKey = getCompanyKey(item.getCompanyId());
            item.setCompanyKey(companyKey);

            Integer departmentKey = getDepartmentKey(item.getCompanyId(), item.getDepartmentId());
            item.setDepartmentKey(departmentKey);

            // sécurité : ignorer la ligne si une clé obligatoire manque
            if (item.getDateKey() == null
                    || item.getUserKey() == null
                    || item.getCompanyKey() == null
                    || item.getDepartmentKey() == null) {
                continue;
            }

            jdbcTemplate.update("""
                INSERT INTO fact_employee_hr (
                    date_key,
                    user_key,
                    company_key,
                    department_key,
                    employee_count,
                    is_active_flag,
                    is_employee_flag,
                    onboarding_flag,
                    offboarding_flag,
                    tenure_days,
                    base_salary,
                    final_salary,
                    taxes_amount,
                    insurance_amount,
                    payroll_count,
                    is_paid_flag
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """,
                    item.getDateKey(),
                    item.getUserKey(),
                    item.getCompanyKey(),
                    item.getDepartmentKey(),
                    item.getEmployeeCount(),
                    item.getIsActiveFlag(),
                    item.getIsEmployeeFlag(),
                    item.getOnboardingFlag(),
                    item.getOffboardingFlag(),
                    item.getTenureDays(),
                    item.getBaseSalary(),
                    item.getFinalSalary(),
                    item.getTaxesAmount(),
                    item.getInsuranceAmount(),
                    item.getPayrollCount(),
                    item.getIsPaidFlag()
            );
        }
    }

    private Integer getDateKey(LocalDate factDate) {
        if (factDate == null) {
            return null;
        }

        if (dateKeyCache.containsKey(factDate)) {
            return dateKeyCache.get(factDate);
        }

        Integer dateKey = jdbcTemplate.query("""
            SELECT date_key
            FROM dim_date
            WHERE full_date = ?
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("date_key") : null, factDate);

        if (dateKey != null) {
            dateKeyCache.put(factDate, dateKey);
        }

        return dateKey;
    }

    private Integer getUserKey(Long companyId, Long userId) {
        if (companyId == null || userId == null) {
            return null;
        }

        String cacheKey = companyId + "|" + userId;

        if (userKeyCache.containsKey(cacheKey)) {
            return userKeyCache.get(cacheKey);
        }

        Integer userKey = jdbcTemplate.query("""
            SELECT user_key
            FROM dim_user
            WHERE company_id = ?
              AND user_id = ?
              AND is_current = TRUE
            ORDER BY user_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("user_key") : null, companyId, userId);

        if (userKey != null) {
            userKeyCache.put(cacheKey, userKey);
        }

        return userKey;
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