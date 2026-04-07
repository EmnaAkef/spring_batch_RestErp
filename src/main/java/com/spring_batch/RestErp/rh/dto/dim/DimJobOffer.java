package com.spring_batch.RestErp.rh.dto.dim;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class DimJobOffer {

    private Integer jobOfferKey;
    private Long jobOfferId;
    private String jobTitle;
    private String employmentType;
    private String location;
    private Timestamp postingDate;
    private Timestamp expiryDate;
    private Integer requiredExperienceYears;
    private BigDecimal salaryRangeMin;
    private BigDecimal salaryRangeMax;
    private String status;
    private Long submittedUserId;

    private Timestamp effectiveFrom;
    private Timestamp effectiveTo;
    private Boolean isCurrent;

    public DimJobOffer() {
    }

    public DimJobOffer(Integer jobOfferKey, Long jobOfferId, String jobTitle, String employmentType,
                       String location, Timestamp postingDate, Timestamp expiryDate,
                       Integer requiredExperienceYears, BigDecimal salaryRangeMin,
                       BigDecimal salaryRangeMax, String status, Long submittedUserId,
                       Timestamp effectiveFrom, Timestamp effectiveTo, Boolean isCurrent) {
        this.jobOfferKey = jobOfferKey;
        this.jobOfferId = jobOfferId;
        this.jobTitle = jobTitle;
        this.employmentType = employmentType;
        this.location = location;
        this.postingDate = postingDate;
        this.expiryDate = expiryDate;
        this.requiredExperienceYears = requiredExperienceYears;
        this.salaryRangeMin = salaryRangeMin;
        this.salaryRangeMax = salaryRangeMax;
        this.status = status;
        this.submittedUserId = submittedUserId;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.isCurrent = isCurrent;
    }

    public Integer getJobOfferKey() {
        return jobOfferKey;
    }

    public void setJobOfferKey(Integer jobOfferKey) {
        this.jobOfferKey = jobOfferKey;
    }

    public Long getJobOfferId() {
        return jobOfferId;
    }

    public void setJobOfferId(Long jobOfferId) {
        this.jobOfferId = jobOfferId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Timestamp postingDate) {
        this.postingDate = postingDate;
    }

    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getRequiredExperienceYears() {
        return requiredExperienceYears;
    }

    public void setRequiredExperienceYears(Integer requiredExperienceYears) {
        this.requiredExperienceYears = requiredExperienceYears;
    }

    public BigDecimal getSalaryRangeMin() {
        return salaryRangeMin;
    }

    public void setSalaryRangeMin(BigDecimal salaryRangeMin) {
        this.salaryRangeMin = salaryRangeMin;
    }

    public BigDecimal getSalaryRangeMax() {
        return salaryRangeMax;
    }

    public void setSalaryRangeMax(BigDecimal salaryRangeMax) {
        this.salaryRangeMax = salaryRangeMax;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSubmittedUserId() {
        return submittedUserId;
    }

    public void setSubmittedUserId(Long submittedUserId) {
        this.submittedUserId = submittedUserId;
    }

    public Timestamp getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Timestamp effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public Timestamp getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(Timestamp effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean current) {
        isCurrent = current;
    }
}