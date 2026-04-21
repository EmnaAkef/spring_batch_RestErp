package com.spring_batch.RestErp.rh.batch.reader.itemReader;

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
                    js.id,
                    js.job_offer_id,
                    jo.user_submited_id,
                    js.status,
                    js.submission_date,
                    js.timestamp
                FROM %s.jobsubmissionmodel js
                LEFT JOIN %s.job_offer_model jo
                       ON jo.id = js.job_offer_id
                ORDER BY js.id
            """.formatted(schemaName, schemaName);

            List<FactJobApplicationSource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {

                FactJobApplicationSource item = new FactJobApplicationSource();

                item.setApplicationId(rs.getLong("id"));
                item.setCompanyId(companyId);
                item.setSchemaName(schemaName);

                item.setJobOfferId((Long) rs.getObject("job_offer_id"));
                item.setCandidateUserId((Long) rs.getObject("user_submited_id"));
                item.setApplicationStatus(rs.getString("status"));

                Timestamp submissionTs = rs.getTimestamp("submission_date");
                if (submissionTs != null) {
                    item.setSubmissionDate(submissionTs.toLocalDateTime());
                } else {
                    Timestamp ts = rs.getTimestamp("timestamp");
                    if (ts != null) {
                        item.setSubmissionDate(ts.toLocalDateTime());
                    }
                }

                item.setIsHired(isHiredStatus(rs.getString("status")));

                return item;
            });

            applications.addAll(result);
        }
    }

    private Boolean isHiredStatus(String status) {
        if (status == null) {
            return false;
        }

        return status.trim().equalsIgnoreCase("ACTIVE");
    }
}