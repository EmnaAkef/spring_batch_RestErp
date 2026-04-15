package com.spring_batch.RestErp.finance.batch.writer.item;

import com.spring_batch.RestErp.finance.dto.fact.FactBill;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class FactBillItemWriter implements ItemWriter<FactBill> {

    private final JdbcTemplate jdbcTemplate;

    private final Map<Long, Integer> companyKeyCache = new HashMap<>();
    private final Map<String, Integer> vendorKeyCache = new HashMap<>();
    private final Map<LocalDate, Integer> dateKeyCache = new HashMap<>();
    private final Map<String, Integer> statusKeyCache = new HashMap<>();

    public FactBillItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends FactBill> chunk) throws Exception {
        for (FactBill item : chunk) {

            Integer companyKey = getCompanyKey(item.getCompanyId());
            if (companyKey == null) {
                throw new IllegalStateException(
                        "company_key introuvable pour company_id = " + item.getCompanyId()
                );
            }

            Integer vendorKey = getVendorKey(item.getCompanyId(), item.getVendorId());

            Integer dateKey = getDateKey(item.getIssueDate() != null ? item.getIssueDate().toLocalDate() : null);
            if (dateKey == null) {
                throw new IllegalStateException(
                        "date_key introuvable pour issueDate = " + item.getIssueDate()
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
            item.setVendorKey(vendorKey);
            item.setDateKey(dateKey);
            item.setDueDateKey(dueDateKey);
            item.setStatusKey(statusKey);

            jdbcTemplate.update("""
                INSERT INTO public.fact_bill (
                    bill_id,
                    date_key,
                    due_date_key,
                    company_key,
                    vendor_key,
                    status_key,
                    total,
                    untaxed_amount,
                    tax,
                    bill_count
                )
                SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM public.fact_bill
                    WHERE bill_id = ?
                      AND company_key = ?
                )
            """,
                    item.getBillId(),
                    item.getDateKey(),
                    item.getDueDateKey(),
                    item.getCompanyKey(),
                    item.getVendorKey(),
                    item.getStatusKey(),
                    item.getTotal(),
                    item.getUntaxedAmount(),
                    item.getTax(),
                    item.getBillCount(),

                    item.getBillId(),
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

    private Integer getVendorKey(Long companyId, Long vendorId) {
        if (companyId == null || vendorId == null) {
            return null;
        }

        String cacheKey = companyId + "_" + vendorId;

        if (vendorKeyCache.containsKey(cacheKey)) {
            return vendorKeyCache.get(cacheKey);
        }

        Integer vendorKey = jdbcTemplate.query("""
            SELECT vendor_key
            FROM public.dim_vendor
            WHERE company_id = ?
              AND vendor_id = ?
              AND is_current = TRUE
            ORDER BY vendor_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("vendor_key") : null, companyId, vendorId);

        if (vendorKey != null) {
            vendorKeyCache.put(cacheKey, vendorKey);
        }

        return vendorKey;
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