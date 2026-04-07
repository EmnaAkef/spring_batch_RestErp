package com.spring_batch.RestErp.rh.dto.source;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class JobOfferSource {

    private Long id;
    private String employmentType;
    private Timestamp expiryDate;
    private String jobTitle;
    private String location;
    private Timestamp postingDate;
    private Integer requiredExperienceYears;
    private BigDecimal salaryRangeMin;
    private BigDecimal salaryRangeMax;
    private String status;
    private Long submittedUserId;
    private Timestamp sourceTimestamp;

    public JobOfferSource() {
    }

    public JobOfferSource(Long id, String employmentType, Timestamp expiryDate, String jobTitle,
                          String location, Timestamp postingDate, Integer requiredExperienceYears,
                          BigDecimal salaryRangeMin, BigDecimal salaryRangeMax,
                          String status, Long submittedUserId, Timestamp sourceTimestamp) {
        this.id = id;
        this.employmentType = employmentType;
        this.expiryDate = expiryDate;
        this.jobTitle = jobTitle;
        this.location = location;
        this.postingDate = postingDate;
        this.requiredExperienceYears = requiredExperienceYears;
        this.salaryRangeMin = salaryRangeMin;
        this.salaryRangeMax = salaryRangeMax;
        this.status = status;
        this.submittedUserId = submittedUserId;
        this.sourceTimestamp = sourceTimestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
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

    public Timestamp getSourceTimestamp() {
        return sourceTimestamp;
    }

    public void setSourceTimestamp(Timestamp sourceTimestamp) {
        this.sourceTimestamp = sourceTimestamp;
    }
}