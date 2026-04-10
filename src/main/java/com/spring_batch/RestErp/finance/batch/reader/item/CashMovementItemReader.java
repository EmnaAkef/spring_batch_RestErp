package com.spring_batch.RestErp.finance.batch.reader.item;

import com.spring_batch.RestErp.finance.dto.source.CashMovementSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CashMovementItemReader implements ItemReader<CashMovementSource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<CashMovementSource> data = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public CashMovementItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public CashMovementSource read() {
        if (!loaded) {
            loadData();
            loaded = true;
        }

        if (nextIndex < data.size()) {
            return data.get(nextIndex++);
        }

        return null;
    }

    private void loadData() {

        List<Map<String, Object>> companies = jdbcTemplate.queryForList("""
            SELECT id, tenant_schema
            FROM public.company
            WHERE tenant_schema IS NOT NULL
              AND archive = false
            ORDER BY id
        """);

        for (Map<String, Object> company : companies) {

            Long companyId = ((Number) company.get("id")).longValue();
            String schema = (String) company.get("tenant_schema");

            if (schema == null || schema.isBlank()) {
                continue;
            }

            String sql = """
                SELECT
                    id,
                    chart_id,
                    users_id,
                    debit,
                    credit,
                    debit_reconciliation,
                    credit_reconciliation,
                    opening_balance,
                    "timestamp"
                FROM %s.journal_entry
                ORDER BY id
            """.formatted(schema);

            List<CashMovementSource> result = jdbcTemplate.query(sql, (rs, i) -> {
                CashMovementSource item = new CashMovementSource();

                item.setCashId(rs.getLong("id"));
                item.setCompanyId(companyId);
                item.setSchemaName(schema);

                item.setChartId((Long) rs.getObject("chart_id"));
                item.setUserId((Long) rs.getObject("users_id"));

                item.setDebit((Double) rs.getObject("debit"));
                item.setCredit((Double) rs.getObject("credit"));
                item.setDebitReconciliation((Double) rs.getObject("debit_reconciliation"));
                item.setCreditReconciliation((Double) rs.getObject("credit_reconciliation"));
                item.setOpeningBalance((Double) rs.getObject("opening_balance"));

                Timestamp ts = rs.getTimestamp("timestamp");
                item.setTimestamp(ts != null ? ts.toLocalDateTime() : null);

                return item;
            });

            data.addAll(result);
        }
    }
}