package com.spring_batch.RestErp.commun.batch.reader.itemReader;

import com.spring_batch.RestErp.commun.dto.source.UserSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserItemReader implements ItemReader<UserSource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<UserSource> users = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public UserItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public UserSource read() {
        if (!loaded) {
            loadUsers();
            loaded = true;
        }

        if (nextIndex < users.size()) {
            return users.get(nextIndex++);
        }

        return null;
    }

    private void loadUsers() {

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
                    u.user_id,
                    u.name,
                    u.gender,
                    u.type,
                    u.active,
                    u.birth_date,
                    u.job_title,
                    u.salary,
                    u.total_hours,
                    u.department_id,
                    d.name AS department_name
                FROM %s.users_table u
                LEFT JOIN %s.department d
                    ON u.department_id = d.id
                ORDER BY u.user_id
            """.formatted(schemaName, schemaName);

            List<UserSource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {

                UserSource user = new UserSource();

                user.setUserId(rs.getLong("user_id"));
                user.setCompanyId(companyId);
                user.setSchemaName(schemaName);

                user.setUserName(rs.getString("name"));
                if (rs.getObject("gender") != null) {
                    user.setGender(rs.getInt("gender"));
                } else {
                    user.setGender(null);
                }
                if (rs.getObject("type") != null) {
                    user.setType(rs.getInt("type"));
                } else {
                    user.setType(null);
                }
                user.setActive(rs.getBoolean("active"));

                if (rs.getDate("birth_date") != null) {
                    user.setBirthdate(rs.getDate("birth_date").toLocalDate());
                } else {
                    user.setBirthdate(null);
                }

                user.setPosition(rs.getString("job_title"));

                if (rs.getObject("salary") != null) {
                    user.setSalary(rs.getDouble("salary"));
                } else {
                    user.setSalary(null);
                }

                if (rs.getObject("total_hours") != null) {
                    user.setTotalHours(rs.getDouble("total_hours"));
                } else {
                    user.setTotalHours(null);
                }

                if (rs.getObject("department_id") != null) {
                    user.setDepartmentId(rs.getLong("department_id"));
                } else {
                    user.setDepartmentId(null);
                }

                user.setDepartmentName(rs.getString("department_name"));

                return user;
            });

            users.addAll(result);
        }
    }
}