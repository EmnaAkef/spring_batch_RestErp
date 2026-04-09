package com.spring_batch.RestErp.sales.batch.writer.itemWriter;

import com.spring_batch.RestErp.sales.dto.fact.FactSalesLine;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SalesLineItemWriter implements ItemWriter<FactSalesLine> {

    private final JdbcTemplate jdbcTemplate;

    private final Map<Long, Integer> companyKeyCache = new HashMap<>();
    private final Map<String, Integer> productKeyCache = new HashMap<>();
    private final Map<LocalDate, Integer> dateKeyCache = new HashMap<>();

    public SalesLineItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends FactSalesLine> chunk) throws Exception {
        for (FactSalesLine item : chunk) {

            Integer companyKey = getCompanyKey(item.getCompanyId());
            if (companyKey == null) {
                throw new IllegalStateException(
                        "Aucune company trouvée dans dim_company pour company_id = " + item.getCompanyId()
                );
            }
            item.setCompanyKey(companyKey);

            Integer productKey = getProductKey(item.getCompanyId(), item.getProductId());
            item.setProductKey(productKey);

            Integer dateKey = getDateKey(item.getSalesDate());
            item.setDateKey(dateKey);

            jdbcTemplate.update("""
                INSERT INTO fact_sales_line (
                    sales_line_id,
                    company_id,
                    company_key,
                    date_key,
                    product_key,
                    quotation_id,
                    invoice_id,
                    sell_order_id,
                    quantity,
                    unit_price,
                    discount,
                    subtotal,
                    tax,
                    line_revenue
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """,
                    item.getSalesLineId(),
                    item.getCompanyId(),
                    item.getCompanyKey(),
                    item.getDateKey(),
                    item.getProductKey(),
                    item.getQuotationId(),
                    item.getInvoiceId(),
                    item.getSellOrderId(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getDiscount(),
                    item.getSubtotal(),
                    item.getTax(),
                    item.getLineRevenue()
            );
        }
    }

    private Integer getCompanyKey(Long companyId) {
        if (companyId == null) {
            return null;
        }

        if (companyKeyCache.containsKey(companyId)) {
            return companyKeyCache.get(companyId);
        }

        Integer companyKey = jdbcTemplate.query("""
            SELECT company_key
            FROM dim_company
            WHERE company_id = ?
              AND is_current = TRUE
            ORDER BY company_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("company_key") : null, companyId);

        if (companyKey != null) {
            companyKeyCache.put(companyId, companyKey);
        }

        return companyKey;
    }

    private Integer getProductKey(Long companyId, Long productId) {
        if (companyId == null || productId == null) {
            return null;
        }

        String cacheKey = companyId + "|" + productId;

        if (productKeyCache.containsKey(cacheKey)) {
            return productKeyCache.get(cacheKey);
        }

        Integer productKey = jdbcTemplate.query("""
            SELECT product_key
            FROM dim_product
            WHERE company_id = ?
              AND product_id = ?
              AND is_current = TRUE
            ORDER BY product_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("product_key") : null, companyId, productId);

        if (productKey != null) {
            productKeyCache.put(cacheKey, productKey);
        }

        return productKey;
    }

    private Integer getDateKey(LocalDate salesDate) {
        if (salesDate == null) {
            return null;
        }

        if (dateKeyCache.containsKey(salesDate)) {
            return dateKeyCache.get(salesDate);
        }

        Integer dateKey = jdbcTemplate.query("""
            SELECT date_key
            FROM dim_date
            WHERE full_date = ?
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("date_key") : null, salesDate);

        if (dateKey != null) {
            dateKeyCache.put(salesDate, dateKey);
        }

        return dateKey;
    }
}