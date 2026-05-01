package com.spring_batch.RestErp.rh.batch.reader.itemReader;

import com.spring_batch.RestErp.rh.dto.source.FactJobOfferSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FactJobOfferItemReader implements ItemReader<FactJobOfferSource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<FactJobOfferSource> jobOffers = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public FactJobOfferItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public FactJobOfferSource read() {
        if (!loaded) {
            loadData();
            loaded = true;
        }

        if (nextIndex < jobOffers.size()) {
            return jobOffers.get(nextIndex++);
        }

        return null;
    }

    private void loadData() {

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
        j.user_submited_id,
        j.status,
        j.posting_date
    FROM %s.job_offer_model j
    WHERE j.user_submited_id IS NOT NULL
      AND j.posting_date IS NOT NULL
    ORDER BY j.id
""".formatted(schemaName);

            List<FactJobOfferSource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {

                FactJobOfferSource item = new FactJobOfferSource();

                item.setJobOfferId(rs.getLong("id"));
                item.setCompanyId(companyId);
                item.setSchemaName(schemaName);

                item.setSubmittedUserId((Long) rs.getObject("user_submited_id"));
                //item.setDepartmentId((Long) rs.getObject("department_id"));
                item.setStatus(rs.getString("status"));

                Timestamp ts = rs.getTimestamp("posting_date");
                item.setPostingDate(ts.toLocalDateTime());

                return item;
            });
            System.out.println("Company: " + schemaName);

            System.out.println("Rows fetched: " + result.size());
            jobOffers.addAll(result);
        }
    }
}