package com.spring_batch.RestErp.rh.batch.reader.item;

import com.spring_batch.RestErp.rh.dto.source.WeeklyShiftTemplateSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeeklyShiftTemplateItemReader implements ItemReader<WeeklyShiftTemplateSource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<WeeklyShiftTemplateSource> templates = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public WeeklyShiftTemplateItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public WeeklyShiftTemplateSource read() {
        if (!loaded) {
            loadTemplates();
            loaded = true;
        }

        if (nextIndex < templates.size()) {
            return templates.get(nextIndex++);
        }

        return null;
    }

    private void loadTemplates() {

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
                    w.creatorid,
                    w.creatorname,
                    w.startdate,
                    w.enddate,
                    w.template_name,
                    w.totalworkhours,
                    w.type
                FROM %s.weekly_time_shifts_template w
                ORDER BY w.id
            """.formatted(schemaName);

            List<WeeklyShiftTemplateSource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {
                WeeklyShiftTemplateSource template = new WeeklyShiftTemplateSource();

                template.setWeeklyTemplateId(rs.getLong("id"));
                template.setCompanyId(companyId);
                template.setSchemaName(schemaName);

                if (rs.getObject("creatorid") != null) {
                    template.setCreatorId(rs.getLong("creatorid"));
                } else {
                    template.setCreatorId(null);
                }

                template.setCreatorName(rs.getString("creatorname"));

                Timestamp startDate = rs.getTimestamp("startdate");
                if (startDate != null) {
                    template.setStartDate(startDate.toLocalDateTime());
                } else {
                    template.setStartDate(null);
                }

                Timestamp endDate = rs.getTimestamp("enddate");
                if (endDate != null) {
                    template.setEndDate(endDate.toLocalDateTime());
                } else {
                    template.setEndDate(null);
                }

                template.setTemplateName(rs.getString("template_name"));

                if (rs.getObject("totalworkhours") != null) {
                    template.setTotalWorkHours(rs.getDouble("totalworkhours"));
                } else {
                    template.setTotalWorkHours(null);
                }

                if (rs.getObject("type") != null) {
                    template.setType(rs.getString("type"));
                } else {
                    template.setType(null);
                }

                return template;
            });

            templates.addAll(result);
        }
    }
}