package com.spring_batch.RestErp.finance.batch.reader;

import com.spring_batch.RestErp.finance.dto.source.VendorSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VendorItemReader implements ItemReader<VendorSource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<VendorSource> vendors = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public VendorItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public VendorSource read() {
        if (!loaded) {
            loadVendors();
            loaded = true;
        }

        if (nextIndex < vendors.size()) {
            return vendors.get(nextIndex++);
        }

        return null;
    }

    private void loadVendors() {

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
                    v.id,
                    v.vendor,
                    v.industry
                FROM %s.vendor v
                ORDER BY v.id
            """.formatted(schemaName);

            List<VendorSource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {
                VendorSource vendor = new VendorSource();

                vendor.setVendorId(rs.getLong("id"));
                vendor.setCompanyId(companyId);
                vendor.setSchemaName(schemaName);

                vendor.setVendorName(rs.getString("vendor"));
                vendor.setIndustry(rs.getString("industry"));


                return vendor;
            });

            vendors.addAll(result);
        }
    }
}
