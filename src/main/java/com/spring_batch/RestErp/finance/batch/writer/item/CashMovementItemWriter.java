package com.spring_batch.RestErp.finance.batch.writer.item;

import com.spring_batch.RestErp.finance.dto.fact.FactCashMovement;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CashMovementItemWriter implements ItemWriter<FactCashMovement> {

    private final JdbcTemplate jdbcTemplate;

    private final Map<Long, Integer> companyKeyCache = new HashMap<>();
    private final Map<String, Integer> chartAccountKeyCache = new HashMap<>();
    private final Map<String, Integer> userKeyCache = new HashMap<>();
    private final Map<LocalDate, Integer> dateKeyCache = new HashMap<>();

    public CashMovementItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends FactCashMovement> chunk) throws Exception {
        for (FactCashMovement item : chunk) {

            Integer companyKey = getCompanyKey(item.getCompanyId());
            if (companyKey == null) {
                throw new IllegalStateException("company_key introuvable pour company_id = " + item.getCompanyId());
            }

            Integer chartAccountKey = getChartAccountKey(item.getCompanyId(), item.getChartId());
            if (chartAccountKey == null) {
                throw new IllegalStateException("chart_key introuvable pour company_id = "
                        + item.getCompanyId() + " et chart_id = " + item.getChartId());
            }

            Integer userKey = getUserKey(item.getCompanyId(), item.getUserId());

            LocalDate movementDate = item.getTimestamp().toLocalDate();
            Integer dateKey = getDateKey(movementDate);
            if (dateKey == null) {
                throw new IllegalStateException("date_key introuvable pour date = " + movementDate);
            }

            item.setCompanyKey(companyKey);
            item.setChartAccountKey(chartAccountKey);
            item.setUserKey(userKey);
            item.setDateKey(dateKey);

            jdbcTemplate.update("""
                INSERT INTO public.fact_cash_movement (
                    cash_id,
                    date_key,
                    chart_account_key,
                    company_key,
                    user_key,
                    debit,
                    credit,
                    net_amount,
                    opening_balance,
                    debit_reconciliation,
                    credit_reconciliation,
                    reconciliation_gap
                )
                SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM public.fact_cash_movement
                    WHERE cash_id = ?
                      AND company_key = ?
                )
            """,
                    item.getCashId(),
                    item.getDateKey(),
                    item.getChartAccountKey(),
                    item.getCompanyKey(),
                    item.getUserKey(),
                    item.getDebit(),
                    item.getCredit(),
                    item.getNetAmount(),
                    item.getOpeningBalance(),
                    item.getDebitReconciliation(),
                    item.getCreditReconciliation(),
                    item.getReconciliationGap(),

                    item.getCashId(),
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

    private Integer getChartAccountKey(Long companyId, Long chartId) {
        if (companyId == null || chartId == null) {
            return null;
        }

        String cacheKey = companyId + "_" + chartId;

        if (chartAccountKeyCache.containsKey(cacheKey)) {
            return chartAccountKeyCache.get(cacheKey);
        }

        Integer chartAccountKey = jdbcTemplate.query("""
            SELECT chart_key
            FROM public.dim_chart_account
            WHERE company_id = ?
              AND chart_id = ?
              AND is_current = TRUE
            ORDER BY chart_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("chart_key") : null, companyId, chartId);

        if (chartAccountKey != null) {
            chartAccountKeyCache.put(cacheKey, chartAccountKey);
        }

        return chartAccountKey;
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
        """, rs -> rs.next() ? rs.getInt("date_key") : null, java.sql.Date.valueOf(date));

        if (dateKey != null) {
            dateKeyCache.put(date, dateKey);
        }

        return dateKey;
    }
}