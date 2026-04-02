package com.emna.spring_batch_test.commun.batch.reader;

import java.util.List;

import javax.sql.DataSource;

import com.emna.spring_batch_test.commun.dto.source.SourceEmployee;
import com.emna.spring_batch_test.commun.service.TenantContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmployeeReader {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeReader(@Qualifier("erpDataSource") DataSource erpDataSource) {
        this.jdbcTemplate = new JdbcTemplate(erpDataSource);
    }

    public List<SourceEmployee> readEmployees(TenantContext tenant) {
        String schema = tenant.getSchemaName();

        String sql = """
                select
                    employee_id,
                    employee_code,
                    first_name,
                    last_name,
                    department_name,
                    hire_date,
                    active
                from %s.employee
                order by employee_id
                """.formatted(schema);

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            SourceEmployee employee = new SourceEmployee();
            employee.setEmployeeId(rs.getLong("employee_id"));
            employee.setEmployeeCode(rs.getString("employee_code"));
            employee.setFirstName(rs.getString("first_name"));
            employee.setLastName(rs.getString("last_name"));
            employee.setDepartmentName(rs.getString("department_name"));
            employee.setHireDate(rs.getDate("hire_date").toLocalDate());
            employee.setActive(rs.getBoolean("active"));
            employee.setTenantIdSource(tenant.getTenantIdSource());
            employee.setSchemaName(schema);
            return employee;
        });
    }
}