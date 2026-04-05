package com.spring_batch.RestErp.commun.dto.source;

public class WorkstatusSource {

    private Long workstatusId;
    private Long companyId;
    private String schemaName;
    private String statusLabel;
    private Long position;
    private Boolean archive;

    public WorkstatusSource() {
    }

    public Long getWorkstatusId() {
        return workstatusId;
    }

    public void setWorkstatusId(Long workstatusId) {
        this.workstatusId = workstatusId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }
}