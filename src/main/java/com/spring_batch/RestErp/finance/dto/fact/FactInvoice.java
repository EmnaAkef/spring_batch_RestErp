package com.spring_batch.RestErp.finance.dto.fact;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FactInvoice {

    private Integer invoiceFactKey;
    private Long invoiceId;

    private Integer dateKey;
    private Integer dueDateKey;
    private Integer companyKey;
    private Integer customerKey;
    private Integer salespersonUserKey;
    private Integer statusKey;

    private Long projectId;
    private Long invoiceNumber;
    private Integer invoiceType;
    private Integer paymentType;

    private BigDecimal total;
    private BigDecimal untaxedAmount;
    private BigDecimal tax;
    private BigDecimal partialPaidAmount;
    private Integer invoiceCount;

    // Champs techniques ETL
    private Long companyId;
    private Long customerId;
    private Long agentId;
    private Integer status;
    private LocalDateTime issueDate;
    private LocalDateTime dueDate;

    public FactInvoice() {
    }

    public Integer getInvoiceFactKey() {
        return invoiceFactKey;
    }

    public void setInvoiceFactKey(Integer invoiceFactKey) {
        this.invoiceFactKey = invoiceFactKey;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
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

    public Integer getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(Integer customerKey) {
        this.customerKey = customerKey;
    }

    public Integer getSalespersonUserKey() {
        return salespersonUserKey;
    }

    public void setSalespersonUserKey(Integer salespersonUserKey) {
        this.salespersonUserKey = salespersonUserKey;
    }

    public Integer getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(Integer statusKey) {
        this.statusKey = statusKey;
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

    public BigDecimal getPartialPaidAmount() {
        return partialPaidAmount;
    }

    public void setPartialPaidAmount(BigDecimal partialPaidAmount) {
        this.partialPaidAmount = partialPaidAmount;
    }

    public Integer getInvoiceCount() {
        return invoiceCount;
    }

    public void setInvoiceCount(Integer invoiceCount) {
        this.invoiceCount = invoiceCount;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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