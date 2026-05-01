package com.spring_batch.RestErp.rh.dto.dim;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class DimJobOffer {

    private Integer jobOfferKey;
    private Long jobOfferId;

    private Long companyId;
    private Integer companyKey;

    private Integer submittedUserKey;
    private Long submittedUserId;

    private String jobTitle;
    private String employmentType;
    private String location;
    private Timestamp postingDate;
    private Timestamp expiryDate;
    private Integer requiredExperienceYears;
    private BigDecimal salaryRangeMin;
    private BigDecimal salaryRangeMax;
    private String status;

    private Timestamp effectiveFrom;
    private Timestamp effectiveTo;
    private Boolean isCurrent;

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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(Integer companyKey) {
        this.companyKey = companyKey;
    }

    public Integer getSubmittedUserKey() {
        return submittedUserKey;
    }

    public void setSubmittedUserKey(Integer submittedUserKey) {
        this.submittedUserKey = submittedUserKey;
    }

    public Long getSubmittedUserId() {
        return submittedUserId;
    }

    public void setSubmittedUserId(Long submittedUserId) {
        this.submittedUserId = submittedUserId;
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