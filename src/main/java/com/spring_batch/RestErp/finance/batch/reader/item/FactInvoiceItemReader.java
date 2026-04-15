package com.spring_batch.RestErp.finance.batch.reader.item;

import com.spring_batch.RestErp.finance.dto.source.FactInvoiceSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FactInvoiceItemReader implements ItemReader<FactInvoiceSource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<FactInvoiceSource> invoices = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public FactInvoiceItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public FactInvoiceSource read() {

        if (!loaded) {
            loadInvoices();
            loaded = true;
        }

        if (nextIndex < invoices.size()) {
            return invoices.get(nextIndex++);
        }

        return null;
    }

    private void loadInvoices() {

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
                    i.id,
                    i.customers_id,
                    i.agent_id,
                    i.status,
                    i.project_id,
                    i.invoicenumber,
                    i.invoicetype,
                    i.payment_type,
                    i.total,
                    i.untaxed_amount,
                    i.tax,
                    i.partialypaidamount,
                    i.issue_date,
                    i.due_date
                FROM %s.invoice i
                ORDER BY i.id
            """.formatted(schemaName);

            List<FactInvoiceSource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {

                FactInvoiceSource invoice = new FactInvoiceSource();

                invoice.setInvoiceId(rs.getLong("id"));
                invoice.setCompanyId(companyId);
                invoice.setSchemaName(schemaName);

                invoice.setCustomerId((Long) rs.getObject("customers_id"));
                invoice.setAgentId((Long) rs.getObject("agent_id"));
                invoice.setStatus((Integer) rs.getObject("status"));

                invoice.setProjectId((Long) rs.getObject("project_id"));
                invoice.setInvoiceNumber((Long) rs.getObject("invoicenumber"));
                invoice.setInvoiceType((Integer) rs.getObject("invoicetype"));
                invoice.setPaymentType((Integer) rs.getObject("payment_type"));

                invoice.setTotal((Double) rs.getObject("total"));
                invoice.setUntaxedAmount((Double) rs.getObject("untaxed_amount"));
                invoice.setTax((Double) rs.getObject("tax"));
                invoice.setPartiallyPaidAmount((Double) rs.getObject("partialypaidamount"));

                Timestamp issueTs = rs.getTimestamp("issue_date");
                if (issueTs != null) {
                    invoice.setIssueDate(issueTs.toLocalDateTime());
                }

                Timestamp dueTs = rs.getTimestamp("due_date");
                if (dueTs != null) {
                    invoice.setDueDate(dueTs.toLocalDateTime());
                }

                return invoice;
            });

            invoices.addAll(result);
        }
    }
}