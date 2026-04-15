package com.spring_batch.RestErp.sales.dto.fact;

import java.time.LocalDate;

public class FactDeal {

    private Long dealId;

    private Long companyId;
    private Integer companyKey;

    private LocalDate closeDate;
    private Integer closeDateKey;

    private Long customerId;
    private Integer customerKey;

    private Long ownerUserId;
    private Integer ownerUserKey;

    private Long workstatusId;
    private Integer workstatusKey;

    private Double dealValue;
    private Integer dealCount;

    private Boolean isClosed;
    private Boolean isArchived;

    public FactDeal() {
    }

    public Long getDealId() {
        return dealId;
    }

    public void setDealId(Long dealId) {
        this.dealId = dealId;
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

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public Integer getCloseDateKey() {
        return closeDateKey;
    }

    public void setCloseDateKey(Integer closeDateKey) {
        this.closeDateKey = closeDateKey;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(Integer customerKey) {
        this.customerKey = customerKey;
    }

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public Integer getOwnerUserKey() {
        return ownerUserKey;
    }

    public void setOwnerUserKey(Integer ownerUserKey) {
        this.ownerUserKey = ownerUserKey;
    }

    public Long getWorkstatusId() {
        return workstatusId;
    }

    public void setWorkstatusId(Long workstatusId) {
        this.workstatusId = workstatusId;
    }

    public Integer getWorkstatusKey() {
        return workstatusKey;
    }

    public void setWorkstatusKey(Integer workstatusKey) {
        this.workstatusKey = workstatusKey;
    }

    public Double getDealValue() {
        return dealValue;
    }

    public void setDealValue(Double dealValue) {
        this.dealValue = dealValue;
    }

    public Integer getDealCount() {
        return dealCount;
    }

    public void setDealCount(Integer dealCount) {
        this.dealCount = dealCount;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }
}