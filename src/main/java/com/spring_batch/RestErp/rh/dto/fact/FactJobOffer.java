package com.spring_batch.RestErp.rh.dto.fact;

import java.time.LocalDateTime;

public class FactJobOffer {

    private Long jobOfferFactKey;

    private Integer postingDateKey;
    private Integer jobOfferKey;
    private Integer createdByUserKey;

    private String status;
    private Integer jobOffersCount;
    private Integer isActiveFlag;

    // Champs techniques ETL
    private Long companyId;
    private Long jobOfferId;
    private Long submittedUserId;
    private LocalDateTime postingDate;

    public FactJobOffer() {
    }

    public Long getJobOfferFactKey() {
        return jobOfferFactKey;
    }

    public void setJobOfferFactKey(Long jobOfferFactKey) {
        this.jobOfferFactKey = jobOfferFactKey;
    }

    public Integer getPostingDateKey() {
        return postingDateKey;
    }

    public void setPostingDateKey(Integer postingDateKey) {
        this.postingDateKey = postingDateKey;
    }

    public Integer getJobOfferKey() {
        return jobOfferKey;
    }

    public void setJobOfferKey(Integer jobOfferKey) {
        this.jobOfferKey = jobOfferKey;
    }

    public Integer getCreatedByUserKey() {
        return createdByUserKey;
    }

    public void setCreatedByUserKey(Integer createdByUserKey) {
        this.createdByUserKey = createdByUserKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getJobOffersCount() {
        return jobOffersCount;
    }

    public void setJobOffersCount(Integer jobOffersCount) {
        this.jobOffersCount = jobOffersCount;
    }

    public Integer getIsActiveFlag() {
        return isActiveFlag;
    }

    public void setIsActiveFlag(Integer isActiveFlag) {
        this.isActiveFlag = isActiveFlag;
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

    public Long getSubmittedUserId() {
        return submittedUserId;
    }

    public void setSubmittedUserId(Long submittedUserId) {
        this.submittedUserId = submittedUserId;
    }

    public LocalDateTime getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(LocalDateTime postingDate) {
        this.postingDate = postingDate;
    }
}