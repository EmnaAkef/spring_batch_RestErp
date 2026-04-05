package com.spring_batch.RestErp.commun.batch.reader.itemReader;

import com.spring_batch.RestErp.commun.dto.source.WorkstatusSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorkstatusItemReader implements ItemReader<WorkstatusSource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<WorkstatusSource> workstatuses = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public WorkstatusItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public WorkstatusSource read() {
        if (!loaded) {
            loadWorkstatuses();
            loaded = true;
        }

        if (nextIndex < workstatuses.size()) {
            return workstatuses.get(nextIndex++);
        }

        return null;
    }

    private void loadWorkstatuses() {

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
                    w.id,
                    w.columnname AS status_label,
                    w.position,
                    w.archive
                FROM %s.workstatus w
                ORDER BY w.id
            """.formatted(schemaName);

            List<WorkstatusSource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {
                WorkstatusSource workstatus = new WorkstatusSource();

                workstatus.setWorkstatusId(rs.getLong("id"));
                workstatus.setCompanyId(companyId);
                workstatus.setSchemaName(schemaName);
                workstatus.setStatusLabel(rs.getString("status_label"));

                if (rs.getObject("position") != null) {
                    workstatus.setPosition(rs.getLong("position"));
                } else {
                    workstatus.setPosition(null);
                }

                if (rs.getObject("archive") != null) {
                    workstatus.setArchive(rs.getBoolean("archive"));
                } else {
                    workstatus.setArchive(null);
                }

                return workstatus;
            });

            workstatuses.addAll(result);
        }
    }
}