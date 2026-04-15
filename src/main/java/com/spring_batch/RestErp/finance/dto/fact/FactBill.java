package com.spring_batch.RestErp.finance.dto.fact;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FactBill {

    private Integer billFactKey;
    private Long billId;

    private Integer dateKey;
    private Integer dueDateKey;
    private Integer companyKey;
    private Integer vendorKey;
    private Integer statusKey;

    private BigDecimal total;
    private BigDecimal untaxedAmount;
    private BigDecimal tax;
    private Integer billCount;

    // Champs techniques ETL
    private Long companyId;
    private Long vendorId;
    private Integer status;
    private LocalDateTime issueDate;
    private LocalDateTime dueDate;

    public FactBill() {
    }

    public Integer getBillFactKey() {
        return billFactKey;
    }

    public void setBillFactKey(Integer billFactKey) {
        this.billFactKey = billFactKey;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Integer getDateKey() {
        return dateKey;
    }

    public void setDateKey(Integer dateKey) {
        this.dateKey = dateKey;
    }

    public Integer getDueDateKey() {
        return dueDateKey;
    }

    public void setDueDateKey(Integer dueDateKey) {
        this.dueDateKey = dueDateKey;
    }

    public Integer getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(Integer companyKey) {
        this.companyKey = companyKey;
    }

    public Integer getVendorKey() {
        return vendorKey;
    }

    public void setVendorKey(Integer vendorKey) {
        this.vendorKey = vendorKey;
    }

    public Integer getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(Integer statusKey) {
        this.statusKey = statusKey;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getUntaxedAmount() {
        return untaxedAmount;
    }

    public void setUntaxedAmount(BigDecimal untaxedAmount) {
        this.untaxedAmount = untaxedAmount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public Integer getBillCount() {
        return billCount;
    }

    public void setBillCount(Integer billCount) {
        this.billCount = billCount;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}