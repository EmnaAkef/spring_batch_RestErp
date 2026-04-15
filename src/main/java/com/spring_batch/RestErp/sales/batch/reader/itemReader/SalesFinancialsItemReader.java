package com.spring_batch.RestErp.sales.batch.reader.itemReader;

import com.spring_batch.RestErp.sales.dto.source.FactSalesFinancialsSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalesFinancialsItemReader implements ItemReader<FactSalesFinancialsSource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<FactSalesFinancialsSource> invoices = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public SalesFinancialsItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public FactSalesFinancialsSource read() {
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
                    i.total,
                    i.agent_id,
                    i.untaxed_amount,
                    i.payment_type,
                    i.status,
                    i.timestamp,

                    COALESCE(r.allocated_amount, 0) AS allocated_amount,
                    COALESCE(r.unallocated_amount, 0) AS unallocated_amount,
                    COALESCE(r.receipt_count, 0) AS receipt_count,

                    COALESCE(q.quotation_total, 0) AS quotation_total,
                    COALESCE(q.quotation_count, 0) AS quotation_count,
                    q.quotation_status

                FROM %s.invoice i

                LEFT JOIN (
                    SELECT
                        customers_id,
                        COALESCE(SUM(allocated_amount), 0) AS allocated_amount,
                        COALESCE(SUM(unallocated_amount), 0) AS unallocated_amount,
                        COUNT(id) AS receipt_count
                    FROM %s.customer_receipt
                    GROUP BY customers_id
                ) r
                    ON r.customers_id = i.customers_id

                LEFT JOIN (
                    SELECT
                        customerid,
                        COALESCE(SUM(total), 0) AS quotation_total,
                        COUNT(id) AS quotation_count,
                        MAX(state) AS quotation_status
                    FROM %s.quotation
                    GROUP BY customerid
                ) q
                    ON q.customerid = i.customers_id

                ORDER BY i.id
            """.formatted(schemaName, schemaName, schemaName);

            List<FactSalesFinancialsSource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {
                FactSalesFinancialsSource invoice = new FactSalesFinancialsSource();

                invoice.setCompanyId(companyId);
                invoice.setSchemaName(schemaName);

                invoice.setInvoiceId(rs.getLong("id"));

                if (rs.getObject("customers_id") != null) {
                    invoice.setCustomerId(rs.getLong("customers_id"));
                } else {
                    invoice.setCustomerId(null);
                }

                if (rs.getObject("total") != null) {
                    invoice.setInvoiceTotal(rs.getDouble("total"));
                } else {
                    invoice.setInvoiceTotal(null);
                }

                if (rs.getObject("untaxed_amount") != null) {
                    invoice.setInvoiceUntaxedAmount(rs.getDouble("untaxed_amount"));
                } else {
                    invoice.setInvoiceUntaxedAmount(null);
                }

                invoice.setPaymentState(rs.getString("payment_type"));

                if (rs.getObject("status") != null) {
                    invoice.setInvoiceStatus(rs.getInt("status"));
                } else {
                    invoice.setInvoiceStatus(null);
                }

                Timestamp ts = rs.getTimestamp("timestamp");
                if (ts != null) {
                    invoice.setInvoiceDate(ts.toLocalDateTime());
                } else {
                    invoice.setInvoiceDate(null);
                }

                invoice.setQuotationId(null);

                if (rs.getObject("allocated_amount") != null) {
                    invoice.setAllocatedAmount(rs.getDouble("allocated_amount"));
                } else {
                    invoice.setAllocatedAmount(0.0);
                }

                if (rs.getObject("unallocated_amount") != null) {
                    invoice.setUnallocatedAmount(rs.getDouble("unallocated_amount"));
                } else {
                    invoice.setUnallocatedAmount(0.0);
                }

                if (rs.getObject("receipt_count") != null) {
                    invoice.setReceiptCount(rs.getInt("receipt_count"));
                } else {
                    invoice.setReceiptCount(0);
                }

                if (rs.getObject("quotation_total") != null) {
                    invoice.setQuotationTotal(rs.getDouble("quotation_total"));
                } else {
                    invoice.setQuotationTotal(0.0);
                }

                if (rs.getObject("quotation_count") != null) {
                    invoice.setQuotationCount(rs.getInt("quotation_count"));
                } else {
                    invoice.setQuotationCount(0);
                }

                if (rs.getObject("quotation_status") != null) {
                    invoice.setQuotationState(String.valueOf(rs.getInt("quotation_status")));
                } else {
                    invoice.setQuotationState(null);
                }

                if (rs.getObject("agent_id") != null) {
                    invoice.setAgentId(rs.getLong("agent_id"));
                } else {
                    invoice.setAgentId(null);
                }

                return invoice;
            });

            invoices.addAll(result);
        }
    }
}