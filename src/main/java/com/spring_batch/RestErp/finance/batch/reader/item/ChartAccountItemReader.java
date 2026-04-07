package com.spring_batch.RestErp.finance.batch.reader.item;

import com.spring_batch.RestErp.finance.dto.source.ChartAccountSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChartAccountItemReader implements ItemReader<ChartAccountSource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<ChartAccountSource> accounts = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public ChartAccountItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ChartAccountSource read() {

        if (!loaded) {
            loadAccounts();
            loaded = true;
        }

        if (nextIndex < accounts.size()) {
            return accounts.get(nextIndex++);
        }

        return null;
    }

    private void loadAccounts() {

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
                    c.id,
                    c.account_name,
                    c.transactionstype,
                    c.account_type
                FROM %s.chart c
                ORDER BY c.id
            """.formatted(schemaName);

            List<ChartAccountSource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {

                ChartAccountSource account = new ChartAccountSource();

                account.setChartId(rs.getLong("id"));
                account.setCompanyId(companyId);
                account.setSchemaName(schemaName);

                account.setAccountName(rs.getString("account_name"));

                if (rs.getObject("transactionstype") != null) {
                    account.setTransactionType(rs.getInt("transactionstype"));
                }

                if (rs.getObject("account_type") != null) {
                    account.setAccountType(rs.getString("account_type"));
                }

                return account;
            });

            accounts.addAll(result);
        }
    }
}
