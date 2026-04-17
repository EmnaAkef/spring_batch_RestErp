package com.spring_batch.RestErp.rh.batch.reader.item;

import com.spring_batch.RestErp.rh.dto.source.FactJobApplicationSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FactJobApplicationItemReader implements ItemReader<FactJobApplicationSource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<FactJobApplicationSource> applications = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public FactJobApplicationItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public FactJobApplicationSource read() {
        if (!loaded) {
            loadApplications();
            loaded = true;
        }

        if (nextIndex < applications.size()) {
            return applications.get(nextIndex++);
        }

        return null;
    }

    private void loadApplications() {

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
                    ja.id,
                    ja.job_offer_id,
                    ja.application_status,
                    ja.submission_date,
                    ja.is_hired
                FROM %s.jobsubmissionmodel ja
                ORDER BY ja.id
            """.formatted(schemaName);

            List<FactJobApplicationSource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {

                FactJobApplicationSource application = new FactJobApplicationSource();

                application.setApplicationId(rs.getLong("id"));
                application.setCompanyId(companyId);
                application.setSchemaName(schemaName);

                application.setJobOfferId((Long) rs.getObject("job_offer_id"));
                application.setApplicationStatus(rs.getString("application_status"));
                application.setIsHired((Boolean) rs.getObject("is_hired"));

                Timestamp submissionTs = rs.getTimestamp("submission_date");
                if (submissionTs != null) {
                    application.setSubmissionDate(submissionTs.toLocalDateTime());
                }

                return application;
            });

            applications.addAll(result);
        }
    }
}