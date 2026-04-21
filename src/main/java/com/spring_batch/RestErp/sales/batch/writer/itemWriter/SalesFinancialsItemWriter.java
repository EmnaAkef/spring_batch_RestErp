package com.spring_batch.RestErp.sales.batch.writer.itemWriter;

import com.spring_batch.RestErp.sales.dto.fact.FactSalesFinancials;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SalesFinancialsItemWriter implements ItemWriter<FactSalesFinancials> {

    private final JdbcTemplate jdbcTemplate;

    private final Map<Long, Integer> companyKeyCache = new HashMap<>();
    private final Map<LocalDate, Integer> dateKeyCache = new HashMap<>();
    private final Map<String, Integer> customerKeyCache = new HashMap<>();
    private final Map<String, Integer> agentUserKeyCache = new HashMap<>();

    public SalesFinancialsItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends FactSalesFinancials> chunk) {
        for (FactSalesFinancials item : chunk) {

            Integer companyKey = getCompanyKey(item.getCompanyId());
            item.setCompanyKey(companyKey);

            Integer dateKey = getDateKey(item.getSalesDate());
            item.setDateKey(dateKey);

            Integer customerKey = getCustomerKey(item.getCompanyId(), item.getCustomerId());
            item.setCustomerKey(customerKey);

            Integer agentUserKey = getAgentUserKey(item.getCompanyId(), item.getAgentId());
            item.setAgentUserKey(agentUserKey);

            // sécurité : ignorer la ligne si les clés minimales sont absentes
            if (item.getInvoiceId() == null || item.getCompanyKey() == null) {
                continue;
            }

            jdbcTemplate.update("""
                INSERT INTO fact_sales_financials (
                    company_id,
                    company_key,
                    date_key,
                    customer_key,
                    agent_user_key,
                    invoice_id,
                    quotation_id,
                    invoice_total,
                    invoice_untaxed_amount,
                    invoice_count,
                    quotation_count,
                    receipt_count,
                    allocated_amount,
                    unallocated_amount,
                    quotation_total,
                    payment_state,
                    invoice_status,
                    quotation_state
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (invoice_id, company_key)
                DO UPDATE SET
                    date_key = EXCLUDED.date_key,
                    customer_key = EXCLUDED.customer_key,
                    agent_user_key = EXCLUDED.agent_user_key,
                    quotation_id = EXCLUDED.quotation_id,
                    invoice_total = EXCLUDED.invoice_total,
                    invoice_untaxed_amount = EXCLUDED.invoice_untaxed_amount,
                    invoice_count = EXCLUDED.invoice_count,
                    quotation_count = EXCLUDED.quotation_count,
                    receipt_count = EXCLUDED.receipt_count,
                    allocated_amount = EXCLUDED.allocated_amount,
                    unallocated_amount = EXCLUDED.unallocated_amount,
                    quotation_total = EXCLUDED.quotation_total,
                    payment_state = EXCLUDED.payment_state,
                    invoice_status = EXCLUDED.invoice_status,
                    quotation_state = EXCLUDED.quotation_state
            """,
                    item.getCompanyId(),
                    item.getCompanyKey(),
                    item.getDateKey(),
                    item.getCustomerKey(),
                    item.getAgentUserKey(),
                    item.getInvoiceId(),
                    item.getQuotationId(),
                    item.getInvoiceTotal(),
                    item.getInvoiceUntaxedAmount(),
                    item.getInvoiceCount(),
                    item.getQuotationCount(),
                    item.getReceiptCount(),
                    item.getAllocatedAmount(),
                    item.getUnallocatedAmount(),
                    item.getQuotationTotal(),
                    item.getPaymentState(),
                    item.getInvoiceStatus(),
                    item.getQuotationState()
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

    private Integer getAgentUserKey(Long companyId, Long agentId) {
        if (companyId == null || agentId == null) {
            return null;
        }

        String cacheKey = companyId + "|" + agentId;

        if (agentUserKeyCache.containsKey(cacheKey)) {
            return agentUserKeyCache.get(cacheKey);
        }

        Integer agentUserKey = jdbcTemplate.query("""
            SELECT user_key
            FROM dim_user
            WHERE company_id = ?
              AND user_id = ?
              AND is_current = TRUE
            ORDER BY user_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("user_key") : null, companyId, agentId);

        if (agentUserKey != null) {
            agentUserKeyCache.put(cacheKey, agentUserKey);
        }

        return agentUserKey;
    }
}