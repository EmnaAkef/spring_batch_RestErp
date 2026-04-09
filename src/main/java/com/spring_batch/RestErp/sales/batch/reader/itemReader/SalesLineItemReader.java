package com.spring_batch.RestErp.sales.batch.reader.itemReader;

import com.spring_batch.RestErp.sales.dto.source.FactSalesLineSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalesLineItemReader implements ItemReader<FactSalesLineSource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<FactSalesLineSource> salesLines = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public SalesLineItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public FactSalesLineSource read() {
        if (!loaded) {
            loadSalesLines();
            loaded = true;
        }

        if (nextIndex < salesLines.size()) {
            return salesLines.get(nextIndex++);
        }

        return null;
    }

    private void loadSalesLines() {

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
                    p.id,
                    p.product_id,
                    p.quotation_id,
                    p.invoice_id,
                    p.sell_order_id,
                    p.quantity,
                    p.unitprice,
                    p.discount,
                    p.subtotal,
                    p.tax,
                    p.timestamp
                FROM %s.product_pack p
                ORDER BY p.id
            """.formatted(schemaName);

            List<FactSalesLineSource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {
                FactSalesLineSource salesLine = new FactSalesLineSource();

                salesLine.setSalesLineId(rs.getLong("id"));
                salesLine.setCompanyId(companyId);
                salesLine.setSchemaName(schemaName);

                if (rs.getObject("product_id") != null) {
                    salesLine.setProductId(rs.getLong("product_id"));
                } else {
                    salesLine.setProductId(null);
                }

                if (rs.getObject("quotation_id") != null) {
                    salesLine.setQuotationId(rs.getLong("quotation_id"));
                } else {
                    salesLine.setQuotationId(null);
                }

                if (rs.getObject("invoice_id") != null) {
                    salesLine.setInvoiceId(rs.getLong("invoice_id"));
                } else {
                    salesLine.setInvoiceId(null);
                }

                if (rs.getObject("sell_order_id") != null) {
                    salesLine.setSellOrderId(rs.getLong("sell_order_id"));
                } else {
                    salesLine.setSellOrderId(null);
                }

                if (rs.getObject("quantity") != null) {
                    salesLine.setQuantity(rs.getDouble("quantity"));
                } else {
                    salesLine.setQuantity(null);
                }

                if (rs.getObject("unitprice") != null) {
                    salesLine.setUnitPrice(rs.getDouble("unitprice"));
                } else {
                    salesLine.setUnitPrice(null);
                }

                if (rs.getObject("discount") != null) {
                    salesLine.setDiscount(rs.getDouble("discount"));
                } else {
                    salesLine.setDiscount(null);
                }

                if (rs.getObject("subtotal") != null) {
                    salesLine.setSubtotal(rs.getDouble("subtotal"));
                } else {
                    salesLine.setSubtotal(null);
                }

                if (rs.getObject("tax") != null) {
                    salesLine.setTax(rs.getDouble("tax"));
                } else {
                    salesLine.setTax(null);
                }

                Timestamp ts = rs.getTimestamp("timestamp");
                if (ts != null) {
                    salesLine.setTimestamp(ts.toLocalDateTime());
                } else {
                    salesLine.setTimestamp(null);
                }

                return salesLine;
            });

            salesLines.addAll(result);
        }
    }
}