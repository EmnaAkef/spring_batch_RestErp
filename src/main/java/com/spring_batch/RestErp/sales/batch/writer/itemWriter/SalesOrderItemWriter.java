package com.spring_batch.RestErp.sales.batch.writer.itemWriter;

import com.spring_batch.RestErp.sales.dto.fact.FactSalesOrder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SalesOrderItemWriter implements ItemWriter<FactSalesOrder> {

    private final JdbcTemplate jdbcTemplate;

    private final Map<Long, Integer> companyKeyCache = new HashMap<>();
    private final Map<LocalDate, Integer> dateKeyCache = new HashMap<>();
    private final Map<String, Integer> customerKeyCache = new HashMap<>();

    public SalesOrderItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends FactSalesOrder> chunk) throws Exception {
        for (FactSalesOrder item : chunk) {

            Integer companyKey = getCompanyKey(item.getCompanyId());
            if (companyKey == null) {
                throw new IllegalStateException(
                        "Aucune company trouvée dans dim_company pour company_id = " + item.getCompanyId()
                );
            }
            item.setCompanyKey(companyKey);

            Integer dateKey = getDateKey(item.getOrderDate());
            item.setDateKey(dateKey);

            Integer customerKey = getCustomerKey(item.getCompanyId(), item.getCustomerId());
            item.setCustomerKey(customerKey);

            jdbcTemplate.update("""
                INSERT INTO fact_sales_order (
                    sell_order_id,
                    company_id,
                    company_key,
                    date_key,
                    customer_key,
                    quotation_id,
                    invoice_id,
                    so_count,
                    status
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """,
                    item.getSellOrderId(),
                    item.getCompanyId(),
                    item.getCompanyKey(),
                    item.getDateKey(),
                    item.getCustomerKey(),
                    item.getQuotationId(),
                    item.getInvoiceId(),
                    item.getSoCount(),
                    item.getStatus()
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

    private Integer getDateKey(LocalDate orderDate) {
        if (orderDate == null) {
            return null;
        }

        if (dateKeyCache.containsKey(orderDate)) {
            return dateKeyCache.get(orderDate);
        }

        Integer dateKey = jdbcTemplate.query("""
            SELECT date_key
            FROM dim_date
            WHERE full_date = ?
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("date_key") : null, orderDate);

        if (dateKey != null) {
            dateKeyCache.put(orderDate, dateKey);
        }

        return dateKey;
    }

    private Integer getCustomerKey(Long companyId, Long customerId) {
        if (companyId == null || customerId == null) {
            return null;
        }

        String cacheKey = companyId + "|" + customerId;

        if (customerKeyCache.containsKey(cacheKey)) {
            return customerKeyCache.get(cacheKey);
        }

        Integer customerKey = jdbcTemplate.query("""
            SELECT customer_key
            FROM dim_customer
            WHERE company_id = ?
              AND customer_id = ?
              AND is_current = TRUE
            ORDER BY customer_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("customer_key") : null, companyId, customerId);

        if (customerKey != null) {
            customerKeyCache.put(cacheKey, customerKey);
        }

        return customerKey;
    }
}