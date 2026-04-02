package com.spring_batch.RestErp.commun.service;

public class TenantContext {

    private Long tenantIdSource;
    private String tenantCode;
    private String tenantName;
    private String schemaName;

    public TenantContext(Long tenantIdSource, String tenantCode, String tenantName, String schemaName) {
        this.tenantIdSource = tenantIdSource;
        this.tenantCode = tenantCode;
        this.tenantName = tenantName;
        this.schemaName = schemaName;
    }

    public Long getTenantIdSource() {
        return tenantIdSource;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public String getTenantName() {
        return tenantName;
    }

    public String getSchemaName() {
        return schemaName;
    }
}