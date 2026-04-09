package com.spring_batch.RestErp.finance.batch.reader.config;

import com.spring_batch.RestErp.finance.batch.reader.mapper.ChartBalanceSnapshotRowMapper;
import com.spring_batch.RestErp.finance.dto.source.ChartBalanceSnapshotSource;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class ChartBalanceSnapshotReaderConfig {

    @Bean
    public JdbcCursorItemReader<ChartBalanceSnapshotSource> chartBalanceSnapshotReader(
            @Qualifier("erpDataSource") DataSource erpDataSource) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(erpDataSource);

        List<Map<String, Object>> companies = jdbcTemplate.queryForList("""
            SELECT id, tenant_schema
            FROM public.company
            WHERE tenant_schema IS NOT NULL
              AND archive = false
            ORDER BY id
        """);

        String unionSql = companies.stream()
                .map(company -> {
                    Long companyId = ((Number) company.get("id")).longValue();
                    String schemaName = (String) company.get("tenant_schema");

                    return """
                        SELECT
                            cb.id AS chart_balance_id,
                            %d AS company_id,
                            '%s' AS schema_name,
                            cb.chart_id,
                            cb."timestamp" AS balance_timestamp,
                            cb.closebalancecredit,
                            cb.closebalancedebit,
                            cb.openbalancecredit,
                            cb.openbalancedebit,
                            cb.totalmovementcredit,
                            cb.totalmovementdebit
                        FROM %s.chart_balance cb
                        """.formatted(companyId, schemaName, schemaName);
                })
                .collect(Collectors.joining(" UNION ALL "));

        String finalSql = unionSql + " ORDER BY company_id, chart_balance_id";

        JdbcCursorItemReader<ChartBalanceSnapshotSource> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(erpDataSource);
        reader.setName("chartBalanceSnapshotReader");
        reader.setSql(finalSql);
        reader.setFetchSize(1000);
        reader.setVerifyCursorPosition(false);
        reader.setRowMapper(new ChartBalanceSnapshotRowMapper());

        return reader;
    }
}