package com.spring_batch.RestErp.sales.batch.reader.itemReader;

import com.spring_batch.RestErp.sales.dto.source.ProductSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductItemReader implements ItemReader<ProductSource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<ProductSource> products = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public ProductItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ProductSource read() {
        if (!loaded) {
            loadProducts();
            loaded = true;
        }

        if (nextIndex < products.size()) {
            return products.get(nextIndex++);
        }

        return null;
    }

    private void loadProducts() {

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
                    p.id,
                    p.product_name,
                    p.category,
                    p.product_type,
                    p.status,
                    p.unit,
                    p.archive
                FROM %s.product p
                ORDER BY p.id
            """.formatted(schemaName);

            List<ProductSource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {
                ProductSource product = new ProductSource();

                product.setProductId(rs.getLong("id"));
                product.setCompanyId(companyId);
                product.setSchemaName(schemaName);

                product.setProductName(rs.getString("product_name"));
                product.setCategory(rs.getString("category"));

                if (rs.getObject("product_type") != null) {
                    product.setProductType(rs.getInt("product_type"));
                } else {
                    product.setProductType(null);
                }

                if (rs.getObject("status") != null) {
                    product.setStatus(rs.getInt("status"));
                } else {
                    product.setStatus(null);
                }

                if (rs.getObject("unit") != null) {
                    product.setUnit(rs.getInt("unit"));
                } else {
                    product.setUnit(null);
                }

                if (rs.getObject("archive") != null) {
                    product.setArchive(rs.getBoolean("archive"));
                } else {
                    product.setArchive(null);
                }

                return product;
            });

            products.addAll(result);
        }
    }
}