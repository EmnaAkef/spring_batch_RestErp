package com.spring_batch.RestErp.sales.batch.reader.itemReader;

import com.spring_batch.RestErp.sales.dto.source.FactDealSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DealItemReader implements ItemReader<FactDealSource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<FactDealSource> deals = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public DealItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public FactDealSource read() {
        if (!loaded) {
            loadDeals();
            loaded = true;
        }

        if (nextIndex < deals.size()) {
            return deals.get(nextIndex++);
        }

        return null;
    }

    private void loadDeals() {
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
                    d.id,
                    d.archive,
                    d.closedate,
                    d.dealvalue,
                    d.timestmp,
                    d.user_id,
                    d.customer_project_id,
                    d.workstatus_deal_id
                FROM %s.dealmodel d
                ORDER BY d.id
            """.formatted(schemaName);

            List<FactDealSource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {
                FactDealSource deal = new FactDealSource();

                deal.setDealId(rs.getLong("id"));
                deal.setCompanyId(companyId);
                deal.setSchemaName(schemaName);

                if (rs.getObject("customer_project_id") != null) {
                    deal.setCustomerId(rs.getLong("customer_project_id"));
                } else {
                    deal.setCustomerId(null);
                }

                if (rs.getObject("user_id") != null) {
                    deal.setOwnerUserId(rs.getLong("user_id"));
                } else {
                    deal.setOwnerUserId(null);
                }

                if (rs.getObject("workstatus_deal_id") != null) {
                    deal.setWorkstatusId(rs.getLong("workstatus_deal_id"));
                } else {
                    deal.setWorkstatusId(null);
                }

                if (rs.getObject("dealvalue") != null) {
                    deal.setDealValue(rs.getDouble("dealvalue"));
                } else {
                    deal.setDealValue(null);
                }

                if (rs.getObject("archive") != null) {
                    deal.setArchived(rs.getBoolean("archive"));
                } else {
                    deal.setArchived(null);
                }

                Timestamp closeTs = rs.getTimestamp("closedate");
                if (closeTs != null) {
                    deal.setCloseDate(closeTs.toLocalDateTime());
                } else {
                    deal.setCloseDate(null);
                }

                Timestamp ts = rs.getTimestamp("timestmp");
                if (ts != null) {
                    deal.setTimestamp(ts.toLocalDateTime());
                } else {
                    deal.setTimestamp(null);
                }

                return deal;
            });

            deals.addAll(result);
        }
    }
}