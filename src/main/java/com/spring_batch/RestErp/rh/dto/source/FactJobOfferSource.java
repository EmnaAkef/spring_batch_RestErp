package com.spring_batch.RestErp.rh.dto.source;

import java.time.LocalDateTime;

public class FactJobOfferSource {

    private Long jobOfferId;
    private Long companyId;
    private String schemaName;

    private Long submittedUserId;
    private String status;
    private LocalDateTime postingDate;

    public FactJobOfferSource() {
    }

    public Long getJobOfferId() {
        return jobOfferId;
    }

    public void setJobOfferId(Long jobOfferId) {
        this.jobOfferId = jobOfferId;
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

    public Long getSubmittedUserId() {
        return submittedUserId;
    }

    public void setSubmittedUserId(Long submittedUserId) {
        this.submittedUserId = submittedUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(LocalDateTime postingDate) {
        this.postingDate = postingDate;
    }
}