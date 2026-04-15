package com.spring_batch.RestErp.finance.dto.source;

import java.time.LocalDateTime;

public class FactInvoiceSource {

    private Long invoiceId;
    private Long companyId;
    private String schemaName;

    private Long customerId;
    private Long agentId;
    private Integer status;

    private Long projectId;
    private Long invoiceNumber;
    private Integer invoiceType;
    private Integer paymentType;

    private Double total;
    private Double untaxedAmount;
    private Double tax;
    private Double partiallyPaidAmount;

    private LocalDateTime issueDate;
    private LocalDateTime dueDate;

    public FactInvoiceSource() {
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Long invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
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

    public Double getPartiallyPaidAmount() {
        return partiallyPaidAmount;
    }

    public void setPartiallyPaidAmount(Double partiallyPaidAmount) {
        this.partiallyPaidAmount = partiallyPaidAmount;
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