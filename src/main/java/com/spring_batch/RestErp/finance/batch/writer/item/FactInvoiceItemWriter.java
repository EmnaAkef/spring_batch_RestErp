package com.spring_batch.RestErp.finance.batch.writer.item;

import com.spring_batch.RestErp.finance.dto.fact.FactInvoice;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class FactInvoiceItemWriter implements ItemWriter<FactInvoice> {

    private final JdbcTemplate jdbcTemplate;

    private final Map<Long, Integer> companyKeyCache = new HashMap<>();
    private final Map<String, Integer> customerKeyCache = new HashMap<>();
    private final Map<String, Integer> userKeyCache = new HashMap<>();
    private final Map<LocalDate, Integer> dateKeyCache = new HashMap<>();
    private final Map<String, Integer> statusKeyCache = new HashMap<>();

    public FactInvoiceItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends FactInvoice> chunk) throws Exception {
        for (FactInvoice item : chunk) {

            Integer companyKey = getCompanyKey(item.getCompanyId());
            if (companyKey == null) {
                throw new IllegalStateException(
                        "company_key introuvable pour company_id = " + item.getCompanyId()
                );
            }

            Integer customerKey = getCustomerKey(item.getCompanyId(), item.getCustomerId());
            Integer salespersonUserKey = getUserKey(item.getCompanyId(), item.getAgentId());

            Integer dateKey = getDateKey(item.getIssueDate() != null ? item.getIssueDate().toLocalDate() : null);
            if (dateKey == null) {
                throw new IllegalStateException(
                        "date_key introuvable pour issue_date = " + item.getIssueDate()
                );
            }

            Integer dueDateKey = getDateKey(item.getDueDate() != null ? item.getDueDate().toLocalDate() : null);

            String statusCode = mapStatusToCode(item.getStatus());
            Integer statusKey = getStatusKey(statusCode);
            if (statusKey == null) {
                throw new IllegalStateException(
                        "status_key introuvable pour status source = " + item.getStatus()
                                + " (status_code = " + statusCode + ")"
                );
            }

            item.setCompanyKey(companyKey);
            item.setCustomerKey(customerKey);
            item.setSalespersonUserKey(salespersonUserKey);
            item.setDateKey(dateKey);
            item.setDueDateKey(dueDateKey);
            item.setStatusKey(statusKey);

            jdbcTemplate.update("""
                INSERT INTO public.fact_invoice (
                    invoice_id,
                    date_key,
                    due_date_key,
                    company_key,
                    customer_key,
                    salesperson_user_key,
                    status_key,
                    project_id,
                    invoicenumber,
                    invoicetype,
                    payment_type,
                    total,
                    untaxed_amount,
                    tax,
                    partial_paid_amount,
                    invoice_count
                )
                SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM public.fact_invoice
                    WHERE invoice_id = ?
                      AND company_key = ?
                )
            """,
                    item.getInvoiceId(),
                    item.getDateKey(),
                    item.getDueDateKey(),
                    item.getCompanyKey(),
                    item.getCustomerKey(),
                    item.getSalespersonUserKey(),
                    item.getStatusKey(),
                    item.getProjectId(),
                    item.getInvoiceNumber(),
                    item.getInvoiceType(),
                    item.getPaymentType(),
                    item.getTotal(),
                    item.getUntaxedAmount(),
                    item.getTax(),
                    item.getPartialPaidAmount(),
                    item.getInvoiceCount(),

                    item.getInvoiceId(),
                    item.getCompanyKey()
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
            FROM public.dim_company
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

    private Integer getCustomerKey(Long companyId, Long customerId) {
        if (companyId == null || customerId == null) {
            return null;
        }

        String cacheKey = companyId + "_" + customerId;

        if (customerKeyCache.containsKey(cacheKey)) {
            return customerKeyCache.get(cacheKey);
        }

        Integer customerKey = jdbcTemplate.query("""
            SELECT customer_key
            FROM public.dim_customer
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

    private Integer getUserKey(Long companyId, Long userId) {
        if (companyId == null || userId == null) {
            return null;
        }

        String cacheKey = companyId + "_" + userId;

        if (userKeyCache.containsKey(cacheKey)) {
            return userKeyCache.get(cacheKey);
        }

        Integer userKey = jdbcTemplate.query("""
            SELECT user_key
            FROM public.dim_user
            WHERE company_id = ?
              AND user_id = ?
              AND is_current = TRUE
            ORDER BY user_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("user_key") : null, companyId, userId);

        if (userKey != null) {
            userKeyCache.put(cacheKey, userKey);
        }

        return userKey;
    }

    private Integer getDateKey(LocalDate date) {
        if (date == null) {
            return null;
        }

        if (dateKeyCache.containsKey(date)) {
            return dateKeyCache.get(date);
        }

        Integer dateKey = jdbcTemplate.query("""
            SELECT date_key
            FROM public.dim_date
            WHERE full_date = ?
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("date_key") : null, Date.valueOf(date));

        if (dateKey != null) {
            dateKeyCache.put(date, dateKey);
        }

        return dateKey;
    }

    private String mapStatusToCode(Integer status) {
        if (status == null) {
            return null;
        }

        return switch (status) {
            case 0 -> "PAID";
            case 1 -> "UNPAID";
            case 2 -> "WAITING";
            case 3 -> "REFUNDED";
            case 4 -> "ACCEPTED";
            case 5 -> "PARTIALLY_PAID";
            case 6 -> "REJECTED";
            case 7 -> "CREDIT_MEMO";
            default -> null;
        };
    }

    private Integer getStatusKey(String statusCode) {
        if (statusCode == null) {
            return null;
        }

        if (statusKeyCache.containsKey(statusCode)) {
            return statusKeyCache.get(statusCode);
        }

        Integer statusKey = jdbcTemplate.query("""
            SELECT status_key
            FROM public.dim_invoice_status
            WHERE status_code = ?
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("status_key") : null, statusCode);

        if (statusKey != null) {
            statusKeyCache.put(statusCode, statusKey);
        }

        return statusKey;
    }
}