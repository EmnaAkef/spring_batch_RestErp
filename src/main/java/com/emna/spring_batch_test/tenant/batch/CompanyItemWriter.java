package com.emna.spring_batch_test.tenant.batch;

import java.sql.Timestamp;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.emna.spring_batch_test.tenant.dto.DimCompany;

public class CompanyItemWriter implements ItemWriter<DimCompany> {

    private final JdbcTemplate jdbcTemplate;

    public CompanyItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends DimCompany> chunk) throws Exception {
        for (DimCompany item : chunk) {

            Timestamp effectiveFrom = Timestamp.valueOf(item.getEffectiveFrom());

            jdbcTemplate.update("""
                UPDATE dim_company
                SET effective_to = ?,
                    is_current = FALSE
                WHERE company_id = ?
                  AND is_current = TRUE
                  AND (
                      COALESCE(company_name, '') <> COALESCE(?, '')
                      OR COALESCE(schema_name, '') <> COALESCE(?, '')
                      OR COALESCE(city, '') <> COALESCE(?, '')
                      OR COALESCE(country, '') <> COALESCE(?, '')
                      OR COALESCE(employee_count, -1) <> COALESCE(?, -1)
                      OR COALESCE(end_salary_month_day, -1) <> COALESCE(?, -1)
                      OR COALESCE(active, FALSE) <> COALESCE(?, FALSE)
                  )
            """,
                    effectiveFrom,
                    item.getCompanyId(),
                    item.getCompanyName(),
                    item.getSchemaName(),
                    item.getCity(),
                    item.getCountry(),
                    item.getEmployeeCount(),
                    item.getEndSalaryMonthDay(),
                    item.getActive()
            );

            jdbcTemplate.update("""
                INSERT INTO dim_company (
                    company_id,
                    company_name,
                    schema_name,
                    city,
                    country,
                    employee_count,
                    end_salary_month_day,
                    active,
                    effective_from,
                    effective_to,
                    is_current
                )
                SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, TRUE
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM dim_company
                    WHERE company_id = ?
                      AND is_current = TRUE
                      AND COALESCE(company_name, '') = COALESCE(?, '')
                      AND COALESCE(schema_name, '') = COALESCE(?, '')
                      AND COALESCE(city, '') = COALESCE(?, '')
                      AND COALESCE(country, '') = COALESCE(?, '')
                      AND COALESCE(employee_count, -1) = COALESCE(?, -1)
                      AND COALESCE(end_salary_month_day, -1) = COALESCE(?, -1)
                      AND COALESCE(active, FALSE) = COALESCE(?, FALSE)
                )
            """,
                    item.getCompanyId(),
                    item.getCompanyName(),
                    item.getSchemaName(),
                    item.getCity(),
                    item.getCountry(),
                    item.getEmployeeCount(),
                    item.getEndSalaryMonthDay(),
                    item.getActive(),
                    effectiveFrom,

                    item.getCompanyId(),
                    item.getCompanyName(),
                    item.getSchemaName(),
                    item.getCity(),
                    item.getCountry(),
                    item.getEmployeeCount(),
                    item.getEndSalaryMonthDay(),
                    item.getActive()
            );
        }
    }
}