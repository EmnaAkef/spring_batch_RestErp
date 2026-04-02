package com.spring_batch.RestErp.commun.batch.reader.itemReader;


import com.spring_batch.RestErp.commun.dto.source.DepartmentSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DepartmentItemReader implements ItemReader<DepartmentSource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<DepartmentSource> departments = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public DepartmentItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public DepartmentSource read() {
        if (!loaded) {
            loadDepartments();
            loaded = true;
        }

        if (nextIndex < departments.size()) {
            return departments.get(nextIndex++);
        }

        return null;
    }

    private void loadDepartments() {
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
                    d.id AS department_id,
                    d.name AS department_name,
                    d.supervisor_id AS supervisor_id,
                    u.name AS supervisor_name
                FROM %s.department d
                LEFT JOIN %s.users_table u
                    ON d.supervisor_id = u.user_id
                ORDER BY d.id
            """.formatted(schemaName, schemaName);

            List<DepartmentSource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {
                DepartmentSource source = new DepartmentSource();

                source.setDepartmentId(rs.getLong("department_id"));
                source.setCompanyId(companyId);
                source.setSchemaName(schemaName);
                source.setDepartmentName(rs.getString("department_name"));

                Object supervisorIdObj = rs.getObject("supervisor_id");
                if (supervisorIdObj != null) {
                    source.setSupervisorId(((Number) supervisorIdObj).longValue());
                } else {
                    source.setSupervisorId(null);
                }

                source.setSupervisorName(rs.getString("supervisor_name"));
                source.setActive(true);

                return source;
            });

            departments.addAll(result);
        }
    }
}
