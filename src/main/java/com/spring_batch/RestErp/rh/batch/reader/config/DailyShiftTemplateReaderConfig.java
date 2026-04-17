package com.spring_batch.RestErp.rh.batch.reader.config;

import com.spring_batch.RestErp.rh.batch.reader.itemReader.DailyShiftTemplateRowMapper;
import com.spring_batch.RestErp.rh.dto.source.DailyShiftTemplateSource;
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
public class DailyShiftTemplateReaderConfig {

    @Bean
    public JdbcCursorItemReader<DailyShiftTemplateSource> dailyShiftTemplateReader(
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
                            d.id AS daily_shift_id,
                            %d AS company_id,
                            '%s' AS schema_name,
                            d.timetemplate_id AS weekly_template_id,
                            d.user_id,
                            d.dayofweek,
                            d.approve,
                            d.generalcheckinstate,
                            d.generalcheckoutstate,
                            d.workinghours,
                            d."timestamp" AS source_timestamp
                        FROM %s.daily_shift d
                        """.formatted(companyId, schemaName, schemaName);
                })
                .collect(Collectors.joining(" UNION ALL "));

        String finalSql = unionSql + " ORDER BY company_id, daily_shift_id";

        JdbcCursorItemReader<DailyShiftTemplateSource> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(erpDataSource);
        reader.setName("dailyShiftTemplateReader");
        reader.setSql(finalSql);
        reader.setFetchSize(1000);
        reader.setVerifyCursorPosition(false);
        reader.setRowMapper(new DailyShiftTemplateRowMapper());

        return reader;
    }
}