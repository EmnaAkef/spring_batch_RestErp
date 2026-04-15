package com.spring_batch.RestErp.finance.batch.reader.item;

import com.spring_batch.RestErp.finance.dto.source.FactBillSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FactBillItemReader implements ItemReader<FactBillSource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<FactBillSource> bills = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public FactBillItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public FactBillSource read() {
        if (!loaded) {
            loadBills();
            loaded = true;
        }

        if (nextIndex < bills.size()) {
            return bills.get(nextIndex++);
        }

        return null;
    }

    private void loadBills() {

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
                    b.id,
                    b.vendor_id,
                    b.status,
                    b.total,
                    b.untaxedamount,
                    b.tax,
                    b.issuedate,
                    b.duedate
                FROM %s.bill b
                ORDER BY b.id
            """.formatted(schemaName);

            List<FactBillSource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {

                FactBillSource bill = new FactBillSource();

                bill.setBillId(rs.getLong("id"));
                bill.setCompanyId(companyId);
                bill.setSchemaName(schemaName);

                bill.setVendorId((Long) rs.getObject("vendor_id"));
                bill.setStatus((Integer) rs.getObject("status"));

                bill.setTotal((Double) rs.getObject("total"));
                bill.setUntaxedAmount((Double) rs.getObject("untaxedamount"));
                bill.setTax((Double) rs.getObject("tax"));

                Timestamp issueTs = rs.getTimestamp("issuedate");
                if (issueTs != null) {
                    bill.setIssueDate(issueTs.toLocalDateTime());
                }

                Timestamp dueTs = rs.getTimestamp("duedate");
                if (dueTs != null) {
                    bill.setDueDate(dueTs.toLocalDateTime());
                }

                return bill;
            });

            bills.addAll(result);
        }
    }
}