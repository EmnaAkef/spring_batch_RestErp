package com.spring_batch.RestErp.sales.batch.reader.itemReader;

import com.spring_batch.RestErp.sales.dto.source.FactSalesOrderSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalesOrderItemReader implements ItemReader<FactSalesOrderSource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<FactSalesOrderSource> salesOrders = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public SalesOrderItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public FactSalesOrderSource read() {
        if (!loaded) {
            loadSalesOrders();
            loaded = true;
        }

        if (nextIndex < salesOrders.size()) {
            return salesOrders.get(nextIndex++);
        }

        return null;
    }

    private void loadSalesOrders() {

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
                    s.id,
                    s.releted_customer_id,
                    s.quotation_id,
                    s.invoice_id,
                    s.status,
                    s.timestamp
                FROM %s.sellsorder s
                ORDER BY s.id
            """.formatted(schemaName);

            List<FactSalesOrderSource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {
                FactSalesOrderSource salesOrder = new FactSalesOrderSource();

                salesOrder.setSellOrderId(rs.getLong("id"));
                salesOrder.setCompanyId(companyId);
                salesOrder.setSchemaName(schemaName);

                if (rs.getObject("releted_customer_id") != null) {
                    salesOrder.setCustomerId(rs.getLong("releted_customer_id"));
                } else {
                    salesOrder.setCustomerId(null);
                }

                if (rs.getObject("quotation_id") != null) {
                    salesOrder.setQuotationId(rs.getLong("quotation_id"));
                } else {
                    salesOrder.setQuotationId(null);
                }

                if (rs.getObject("invoice_id") != null) {
                    salesOrder.setInvoiceId(rs.getLong("invoice_id"));
                } else {
                    salesOrder.setInvoiceId(null);
                }

                if (rs.getObject("status") != null) {
                    salesOrder.setStatus(rs.getInt("status"));
                } else {
                    salesOrder.setStatus(null);
                }

                Timestamp ts = rs.getTimestamp("timestamp");
                if (ts != null) {
                    salesOrder.setOrderDate(ts.toLocalDateTime());
                } else {
                    salesOrder.setOrderDate(null);
                }

                return salesOrder;
            });

            salesOrders.addAll(result);
        }
    }
}