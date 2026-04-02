package com.emna.spring_batch_test.commun.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TenantRegistryService {

    private final JdbcTemplate jdbcTemplate;

    public TenantRegistryService(@Qualifier("dwDataSource") DataSource dwDataSource) {
        this.jdbcTemplate = new JdbcTemplate(dwDataSource);
    }

    public List<TenantContext> getActiveTenants() {
        return jdbcTemplate.query("""
                select tenant_id_source, tenant_code, tenant_name, schema_name
                from dim_tenant
                where active = true
                order by tenant_key
                """,
                (rs, rowNum) -> new TenantContext(
                        rs.getLong("tenant_id_source"),
                        rs.getString("tenant_code"),
                        rs.getString("tenant_name"),
                        rs.getString("schema_name")
                )
        );
    }
}