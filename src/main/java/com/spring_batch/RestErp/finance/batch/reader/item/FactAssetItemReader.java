package com.spring_batch.RestErp.finance.batch.reader.item;

import com.spring_batch.RestErp.finance.dto.source.FactAssetSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FactAssetItemReader implements ItemReader<FactAssetSource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<FactAssetSource> assets = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public FactAssetItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public FactAssetSource read() {
        if (!loaded) {
            loadAssets();
            loaded = true;
        }

        if (nextIndex < assets.size()) {
            return assets.get(nextIndex++);
        }

        return null;
    }

    private void loadAssets() {

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
                    a.id,
                    a.assign_date,
                    a.depreciation_amount,
                    a.percentage,
                    a.years_duration,
                    a.type,
                    a.value
                FROM %s.asset a
                ORDER BY a.id
            """.formatted(schemaName);

            List<FactAssetSource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {

                FactAssetSource asset = new FactAssetSource();

                asset.setAssetId(rs.getLong("id"));
                asset.setCompanyId(companyId);
                asset.setSchemaName(schemaName);

                asset.setAssetType(rs.getString("type"));
                asset.setAssetValue((Double) rs.getObject("value"));
                asset.setDepreciationAmount((Double) rs.getObject("depreciation_amount"));
                asset.setPercentage((Double) rs.getObject("percentage"));
                asset.setYearsDuration((Integer) rs.getObject("years_duration"));

                Timestamp assignTs = rs.getTimestamp("assign_date");
                if (assignTs != null) {
                    asset.setAssignDate(assignTs.toLocalDateTime());
                }

                return asset;
            });

            assets.addAll(result);
        }
    }
}