package com.spring_batch.RestErp.commun.dto.source;

public class DepartmentSource {

    private Long departmentId;
    private Long companyId;
    private String schemaName;
    private String departmentName;
    private Long supervisorId;
    private String supervisorName;
    private Boolean active;

    public DepartmentSource() {}

    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getSchemaName() { return schemaName; }
    public void setSchemaName(String schemaName) { this.schemaName = schemaName; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public Long getSupervisorId() { return supervisorId; }
    public void setSupervisorId(Long supervisorId) { this.supervisorId = supervisorId; }

    public String getSupervisorName() { return supervisorName; }
    public void setSupervisorName(String supervisorName) { this.supervisorName = supervisorName; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}