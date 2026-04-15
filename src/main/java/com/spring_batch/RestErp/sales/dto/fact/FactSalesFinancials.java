package com.spring_batch.RestErp.sales.dto.fact;

import java.time.LocalDate;

public class FactSalesFinancials {

    private Long companyId;
    private Integer companyKey;

    private LocalDate salesDate;
    private Integer dateKey;

    private Long customerId;
    private Integer customerKey;

    private Long agentId;
    private Integer agentUserKey;

    private Long invoiceId;
    private Long quotationId;

    private Double invoiceTotal;
    private Double invoiceUntaxedAmount;

    private Integer invoiceCount;
    private Integer quotationCount;
    private Integer receiptCount;

    private Double allocatedAmount;
    private Double unallocatedAmount;
    private Double quotationTotal;

    private String paymentState;
    private String invoiceStatus;
    private String quotationState;

    public FactSalesFinancials() {
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

    public LocalDate getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(LocalDate salesDate) {
        this.salesDate = salesDate;
    }

    public Integer getDateKey() {
        return dateKey;
    }

    public void setDateKey(Integer dateKey) {
        this.dateKey = dateKey;
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

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public Double getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(Double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    public Double getInvoiceUntaxedAmount() {
        return invoiceUntaxedAmount;
    }

    public void setInvoiceUntaxedAmount(Double invoiceUntaxedAmount) {
        this.invoiceUntaxedAmount = invoiceUntaxedAmount;
    }

    public Integer getInvoiceCount() {
        return invoiceCount;
    }

    public void setInvoiceCount(Integer invoiceCount) {
        this.invoiceCount = invoiceCount;
    }

    public Integer getQuotationCount() {
        return quotationCount;
    }

    public void setQuotationCount(Integer quotationCount) {
        this.quotationCount = quotationCount;
    }

    public Integer getReceiptCount() {
        return receiptCount;
    }

    public void setReceiptCount(Integer receiptCount) {
        this.receiptCount = receiptCount;
    }

    public Double getAllocatedAmount() {
        return allocatedAmount;
    }

    public void setAllocatedAmount(Double allocatedAmount) {
        this.allocatedAmount = allocatedAmount;
    }

    public Double getUnallocatedAmount() {
        return unallocatedAmount;
    }

    public void setUnallocatedAmount(Double unallocatedAmount) {
        this.unallocatedAmount = unallocatedAmount;
    }

    public Double getQuotationTotal() {
        return quotationTotal;
    }

    public void setQuotationTotal(Double quotationTotal) {
        this.quotationTotal = quotationTotal;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getQuotationState() {
        return quotationState;
    }

    public void setQuotationState(String quotationState) {
        this.quotationState = quotationState;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Integer getAgentUserKey() {
        return agentUserKey;
    }

    public void setAgentUserKey(Integer agentUserKey) {
        this.agentUserKey = agentUserKey;
    }
}