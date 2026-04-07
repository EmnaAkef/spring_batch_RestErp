package com.spring_batch.RestErp.rh.batch.reader.item;



import com.spring_batch.RestErp.rh.dto.source.JobOfferSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JobOfferItemReader implements ItemReader<JobOfferSource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<JobOfferSource> jobOffers = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public JobOfferItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public JobOfferSource read() {
        if (!loaded) {
            loadJobOffers();
            loaded = true;
        }

        if (nextIndex < jobOffers.size()) {
            return jobOffers.get(nextIndex++);
        }

        return null;
    }

    private void loadJobOffers() {

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
                    j.id,
                    j.employment_type,
                    j.expiry_date,
                    j.jobtitle,
                    j.location,
                    j.posting_date,
                    j.required_experience_years,
                    j.salary_range_min,
                    j.salary_range_max,
                    j.status,
                    j.user_submited_id,
                    j."timestamp"
                FROM %s.job_offer_model j
                ORDER BY j.id
            """.formatted(schemaName);

            List<JobOfferSource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {
                JobOfferSource jobOffer = new JobOfferSource();

                jobOffer.setId(rs.getLong("id"));
                jobOffer.setEmploymentType(rs.getString("employment_type"));
                jobOffer.setExpiryDate(rs.getTimestamp("expiry_date"));
                jobOffer.setJobTitle(rs.getString("jobtitle"));
                jobOffer.setLocation(rs.getString("location"));
                jobOffer.setPostingDate(rs.getTimestamp("posting_date"));
                jobOffer.setRequiredExperienceYears((Integer) rs.getObject("required_experience_years"));
                jobOffer.setSalaryRangeMin(rs.getBigDecimal("salary_range_min"));
                jobOffer.setSalaryRangeMax(rs.getBigDecimal("salary_range_max"));
                jobOffer.setStatus(rs.getString("status"));
                jobOffer.setSubmittedUserId((Long) rs.getObject("user_submited_id"));
                jobOffer.setSourceTimestamp(rs.getTimestamp("timestamp"));

                return jobOffer;
            });

            jobOffers.addAll(result);
        }
    }
}