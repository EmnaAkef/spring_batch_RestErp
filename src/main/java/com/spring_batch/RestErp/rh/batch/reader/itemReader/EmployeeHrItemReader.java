package com.spring_batch.RestErp.rh.batch.reader.itemReader;

import com.spring_batch.RestErp.rh.dto.source.FactEmployeeHrSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeHrItemReader implements ItemReader<FactEmployeeHrSource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<FactEmployeeHrSource> rows = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public EmployeeHrItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public FactEmployeeHrSource read() {
        if (!loaded) {
            loadRows();
            loaded = true;
        }

        if (nextIndex < rows.size()) {
            return rows.get(nextIndex++);
        }

        return null;
    }

    private void loadRows() {

        List<Map<String, Object>> companies = jdbcTemplate.queryForList("""
            SELECT id, tenant_schema
            FROM public.company
            WHERE tenant_schema IS NOT NULL
              AND archive = false
            ORDER BY id
        """);

        for (Map<String, Object> company : companies) {

            Long companyId = ((Number) company.get("id")).longValue();
            String schemaName = (String) company.get("tenant_schema");

            if (schemaName == null || schemaName.isBlank()) {
                continue;
            }

            String sql = """
                SELECT
                            p.padydate AS fact_date,
                    
                            u.user_id,
                            u.department_id,
                            u.active,
                            u.type,
                            u.contract_start,
                            u.contract_end,
                            u.salary AS base_salary,
                    
                            s.final_salary,
                            s.taxes AS taxes_amount,
                            s.insurance AS insurance_amount,
                    
                            1 AS payroll_count,
                    
                            CASE
                                WHEN p.paiementstate = 1 THEN 1
                                ELSE 0
                            END AS is_paid_flag
                    
                        FROM %s.users_table u
                    
                        LEFT JOIN %s.salary s
                            ON s.user_id = u.user_id
                    
                        LEFT JOIN %s.payslip p
                            ON p.id = s.payslip_id
                    
                        WHERE u.department_id IS NOT NULL
                          AND p.padydate IS NOT NULL
            """.formatted(schemaName, schemaName, schemaName);

            List<FactEmployeeHrSource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {
                FactEmployeeHrSource row = new FactEmployeeHrSource();

                Date factDate = rs.getDate("fact_date");
                if (factDate != null) {
                    row.setFactDate(factDate.toLocalDate());
                } else {
                    row.setFactDate(null);
                }

                if (rs.getObject("user_id") != null) {
                    row.setUserId(rs.getLong("user_id"));
                } else {
                    row.setUserId(null);
                }

                row.setCompanyId(companyId);

                if (rs.getObject("department_id") != null) {
                    row.setDepartmentId(rs.getLong("department_id"));
                } else {
                    row.setDepartmentId(null);
                }

                if (rs.getObject("active") != null) {
                    row.setActive(rs.getBoolean("active"));
                } else {
                    row.setActive(null);
                }

                row.setType(rs.getString("type"));

                Date contractStart = rs.getDate("contract_start");
                if (contractStart != null) {
                    row.setContractStart(contractStart.toLocalDate());
                } else {
                    row.setContractStart(null);
                }

                Date contractEnd = rs.getDate("contract_end");
                if (contractEnd != null) {
                    row.setContractEnd(contractEnd.toLocalDate());
                } else {
                    row.setContractEnd(null);
                }

                if (rs.getObject("base_salary") != null) {
                    row.setBaseSalary(rs.getDouble("base_salary"));
                } else {
                    row.setBaseSalary(null);
                }

                if (rs.getObject("final_salary") != null) {
                    row.setFinalSalary(rs.getDouble("final_salary"));
                } else {
                    row.setFinalSalary(null);
                }

                if (rs.getObject("taxes_amount") != null) {
                    row.setTaxesAmount(rs.getDouble("taxes_amount"));
                } else {
                    row.setTaxesAmount(null);
                }

                if (rs.getObject("insurance_amount") != null) {
                    row.setInsuranceAmount(rs.getDouble("insurance_amount"));
                } else {
                    row.setInsuranceAmount(null);
                }

                if (rs.getObject("payroll_count") != null) {
                    row.setPayrollCount(rs.getInt("payroll_count"));
                } else {
                    row.setPayrollCount(0);
                }

                if (rs.getObject("is_paid_flag") != null) {
                    row.setIsPaidFlag(rs.getInt("is_paid_flag"));
                } else {
                    row.setIsPaidFlag(0);
                }

                return row;
            });

            rows.addAll(result);
        }
    }
}