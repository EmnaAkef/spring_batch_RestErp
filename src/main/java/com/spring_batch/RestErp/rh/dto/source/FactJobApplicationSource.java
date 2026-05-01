package com.spring_batch.RestErp.rh.dto.source;

import java.time.LocalDateTime;

public class FactJobApplicationSource {

    private Long applicationId;
    private Long companyId;
    private String schemaName;

    private Long jobOfferId;
    private String applicationStatus;
    private LocalDateTime submissionDate;
    private Boolean isHired;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
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

    public Long getJobOfferId() {
        return jobOfferId;
    }

    public void setJobOfferId(Long jobOfferId) {
        this.jobOfferId = jobOfferId;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Boolean getIsHired() {
        return isHired;
    }

    public void setIsHired(Boolean hired) {
        isHired = hired;
    }
}