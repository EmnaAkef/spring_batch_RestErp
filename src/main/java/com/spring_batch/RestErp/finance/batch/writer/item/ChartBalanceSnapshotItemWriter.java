package com.spring_batch.RestErp.finance.batch.writer.item;

import com.spring_batch.RestErp.finance.dto.source.ChartBalanceSnapshotSource;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ChartBalanceSnapshotItemWriter implements ItemWriter<ChartBalanceSnapshotSource> {

    private final JdbcTemplate jdbcTemplate;

    private final Map<Long, Integer> companyKeyCache = new HashMap<>();
    private final Map<String, Integer> chartAccountKeyCache = new HashMap<>();
    private final Map<LocalDate, Integer> dateKeyCache = new HashMap<>();

    public ChartBalanceSnapshotItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends ChartBalanceSnapshotSource> chunk) {
        for (ChartBalanceSnapshotSource item : chunk) {

            Integer companyKey = getCompanyKey(item.getCompanyId());
            LocalDate balanceDate = item.getTimestamp() != null ? item.getTimestamp().toLocalDate() : null;
            Integer dateKey = getDateKey(balanceDate);
            Integer chartAccountKey = getChartAccountKey(item.getCompanyId(), item.getChartId());

            if (companyKey == null || dateKey == null || chartAccountKey == null) {
                continue;
            }

            jdbcTemplate.update("""
                INSERT INTO public.fact_chart_balance_snapshot (
                    chart_account_key,
                    date_key,
                    company_key,
                    close_balance_debit,
                    close_balance_credit,
                    open_balance_debit,
                    open_balance_credit,
                    total_movement_debit,
                    total_movement_credit
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (chart_account_key, date_key, company_key)
                DO UPDATE SET
                    close_balance_debit = EXCLUDED.close_balance_debit,
                    close_balance_credit = EXCLUDED.close_balance_credit,
                    open_balance_debit = EXCLUDED.open_balance_debit,
                    open_balance_credit = EXCLUDED.open_balance_credit,
                    total_movement_debit = EXCLUDED.total_movement_debit,
                    total_movement_credit = EXCLUDED.total_movement_credit
            """,
                    chartAccountKey,
                    dateKey,
                    companyKey,
                    item.getCloseBalanceDebit(),
                    item.getCloseBalanceCredit(),
                    item.getOpenBalanceDebit(),
                    item.getOpenBalanceCredit(),
                    item.getTotalMovementDebit(),
                    item.getTotalMovementCredit()
            );
        }
    }

    private Integer getCompanyKey(Long companyId) {
        if (companyId == null) return null;
        if (companyKeyCache.containsKey(companyId)) return companyKeyCache.get(companyId);

        Integer companyKey = jdbcTemplate.query("""
            SELECT company_key
            FROM public.dim_company
            WHERE company_id = ?
              AND is_current = TRUE
            ORDER BY company_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("company_key") : null, companyId);

        if (companyKey != null) companyKeyCache.put(companyId, companyKey);
        return companyKey;
    }

    private Integer getDateKey(LocalDate date) {
        if (date == null) return null;
        if (dateKeyCache.containsKey(date)) return dateKeyCache.get(date);

        Integer dateKey = jdbcTemplate.query("""
            SELECT date_key
            FROM public.dim_date
            WHERE full_date = ?
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("date_key") : null, java.sql.Date.valueOf(date));

        if (dateKey != null) dateKeyCache.put(date, dateKey);
        return dateKey;
    }

    private Integer getChartAccountKey(Long companyId, Long chartId) {
        if (companyId == null || chartId == null) return null;

        String cacheKey = companyId + "_" + chartId;
        if (chartAccountKeyCache.containsKey(cacheKey)) return chartAccountKeyCache.get(cacheKey);

        Integer chartAccountKey = jdbcTemplate.query("""
            SELECT chart_key
            FROM public.dim_chart_account
            WHERE company_id = ?
              AND chart_id = ?
              AND is_current = TRUE
            ORDER BY chart_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("chart_key") : null, companyId, chartId);

        if (chartAccountKey != null) chartAccountKeyCache.put(cacheKey, chartAccountKey);
        return chartAccountKey;
    }
}