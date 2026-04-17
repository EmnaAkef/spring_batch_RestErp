package com.spring_batch.RestErp.rh.batch.reader.itemReader;

import com.spring_batch.RestErp.rh.dto.source.FactAbsenceMonthlySource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbsenceMonthlyItemReader implements ItemReader<FactAbsenceMonthlySource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<FactAbsenceMonthlySource> rows = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public AbsenceMonthlyItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public FactAbsenceMonthlySource read() {
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
                    DATE_TRUNC('month', pts.timestamp)::date AS month_date,
                    u.department_id,

                    COUNT(*) AS scheduled_shifts_count,

                    SUM(
                        CASE
                            WHEN pts.check_in_state = 1 AND pts.check_out_state = 1
                            THEN 1 ELSE 0
                        END
                    ) AS absent_shifts_count,

                    ROUND(
                        (
                            SUM(
                                CASE
                                    WHEN pts.check_in_state = 1 AND pts.check_out_state = 1
                                    THEN 1 ELSE 0
                                END
                            ) * 100.0
                        ) / NULLIF(COUNT(*), 0),
                        2
                    ) AS absence_rate_pct

                FROM %s.part_time_shift pts
                JOIN %s.users_table u
                    ON u.user_id = pts.user_id

                WHERE u.department_id IS NOT NULL
                  AND pts.timestamp IS NOT NULL

                GROUP BY
                    DATE_TRUNC('month', pts.timestamp)::date,
                    u.department_id

                ORDER BY
                    month_date,
                    u.department_id
            """.formatted(schemaName, schemaName);

            List<FactAbsenceMonthlySource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {
                FactAbsenceMonthlySource row = new FactAbsenceMonthlySource();

                Date monthDate = rs.getDate("month_date");
                if (monthDate != null) {
                    row.setMonthDate(monthDate.toLocalDate());
                } else {
                    row.setMonthDate(null);
                }

                row.setCompanyId(companyId);

                if (rs.getObject("department_id") != null) {
                    row.setDepartmentId(rs.getLong("department_id"));
                } else {
                    row.setDepartmentId(null);
                }

                if (rs.getObject("scheduled_shifts_count") != null) {
                    row.setScheduledShiftsCount(rs.getInt("scheduled_shifts_count"));
                } else {
                    row.setScheduledShiftsCount(0);
                }

                if (rs.getObject("absent_shifts_count") != null) {
                    row.setAbsentShiftsCount(rs.getInt("absent_shifts_count"));
                } else {
                    row.setAbsentShiftsCount(0);
                }

                if (rs.getObject("absence_rate_pct") != null) {
                    row.setAbsenceRatePct(rs.getDouble("absence_rate_pct"));
                } else {
                    row.setAbsenceRatePct(0.0);
                }

                return row;
            });

            rows.addAll(result);
        }
    }
}