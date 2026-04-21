package com.spring_batch.RestErp.rh.dto.fact;

import java.time.LocalDateTime;

public class FactJobApplication {

    private Long jobAppFactKey;

    private Integer submissionDateKey;
    private Integer jobOfferKey;
    private Integer candidateUserKey;

    private String applicationStatus;
    private Integer applicationsCount;
    private Integer isHiredFlag;

    // champs techniques ETL
    private Long companyId;
    private Long jobOfferId;
    private Long candidateUserId;
    private LocalDateTime submissionDate;

    public FactJobApplication() {
    }

    public Long getJobAppFactKey() {
        return jobAppFactKey;
    }

    public void setJobAppFactKey(Long jobAppFactKey) {
        this.jobAppFactKey = jobAppFactKey;
    }

    public Integer getSubmissionDateKey() {
        return submissionDateKey;
    }

    public void setSubmissionDateKey(Integer submissionDateKey) {
        this.submissionDateKey = submissionDateKey;
    }

    public Integer getJobOfferKey() {
        return jobOfferKey;
    }

    public void setJobOfferKey(Integer jobOfferKey) {
        this.jobOfferKey = jobOfferKey;
    }

    public Integer getCandidateUserKey() {
        return candidateUserKey;
    }

    public void setCandidateUserKey(Integer candidateUserKey) {
        this.candidateUserKey = candidateUserKey;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Integer getApplicationsCount() {
        return applicationsCount;
    }

    public void setApplicationsCount(Integer applicationsCount) {
        this.applicationsCount = applicationsCount;
    }

    public Integer getIsHiredFlag() {
        return isHiredFlag;
    }

    public void setIsHiredFlag(Integer isHiredFlag) {
        this.isHiredFlag = isHiredFlag;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getJobOfferId() {
        return jobOfferId;
    }

    public void setJobOfferId(Long jobOfferId) {
        this.jobOfferId = jobOfferId;
    }

    public Long getCandidateUserId() {
        return candidateUserId;
    }

    public void setCandidateUserId(Long candidateUserId) {
        this.candidateUserId = candidateUserId;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }
}