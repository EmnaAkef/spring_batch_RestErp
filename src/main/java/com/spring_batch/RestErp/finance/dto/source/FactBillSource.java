package com.spring_batch.RestErp.finance.dto.source;

import java.time.LocalDateTime;

public class FactBillSource {

    private Long billId;
    private Long companyId;
    private String schemaName;

    private Long vendorId;
    private Integer status;

    private Double total;
    private Double untaxedAmount;
    private Double tax;

    private LocalDateTime issueDate;
    private LocalDateTime dueDate;

    public FactBillSource() {
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getUntaxedAmount() {
        return untaxedAmount;
    }

    public void setUntaxedAmount(Double untaxedAmount) {
        this.untaxedAmount = untaxedAmount;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
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