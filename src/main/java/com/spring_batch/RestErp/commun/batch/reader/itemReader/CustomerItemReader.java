package com.spring_batch.RestErp.commun.batch.reader.itemReader;

import com.spring_batch.RestErp.commun.dto.source.CustomerSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerItemReader implements ItemReader<CustomerSource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<CustomerSource> customers = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public CustomerItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public CustomerSource read() {
        if (!loaded) {
            loadCustomers();
            loaded = true;
        }

        if (nextIndex < customers.size()) {
            return customers.get(nextIndex++);
        }

        return null;
    }

    private void loadCustomers() {

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
                    c.clientcategory,
                    c.type,
                    c.status,
                    c.active,
                    c.city,
                    c.nationality,
                    c.contact_name,
                    c.organization_name
                FROM %s.customer c
                ORDER BY c.id
            """.formatted(schemaName);

            List<CustomerSource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {
                CustomerSource customer = new CustomerSource();

                customer.setCustomerId(rs.getLong("id"));
                customer.setCompanyId(companyId);
                customer.setSchemaName(schemaName);

                if (rs.getObject("clientcategory") != null) {
                    customer.setClientCategory(rs.getInt("clientcategory"));
                } else {
                    customer.setClientCategory(null);
                }

                if (rs.getObject("type") != null) {
                    customer.setType(rs.getInt("type"));
                } else {
                    customer.setType(null);
                }

                customer.setStatus(rs.getString("status"));
                customer.setActive(rs.getObject("active") != null ? rs.getBoolean("active") : null);
                customer.setCity(rs.getString("city"));
                customer.setNationality(rs.getString("nationality"));
                customer.setContactName(rs.getString("contact_name"));
                customer.setOrganizationName(rs.getString("organization_name"));

                return customer;
            });

            customers.addAll(result);
        }
    }
}