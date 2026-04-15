package com.spring_batch.RestErp.sales.dto.source;

import java.time.LocalDateTime;

public class FactSalesFinancialsSource {

    private Long companyId;
    private String schemaName;

    private Long invoiceId;
    private Long quotationId;
    private Long customerId;
    private Long agentId;

    private Double invoiceTotal;
    private Double invoiceUntaxedAmount;

    private String paymentState;
    private Integer invoiceStatus;
    private String quotationState;

    private Double allocatedAmount;
    private Double unallocatedAmount;
    private Integer receiptCount;

    private Double quotationTotal;
    private Integer quotationCount;

    private LocalDateTime invoiceDate;

    public FactSalesFinancialsSource() {
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

    public Integer  getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Integer  invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getQuotationState() {
        return quotationState;
    }

    public void setQuotationState(String quotationState) {
        this.quotationState = quotationState;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
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

    public Integer getReceiptCount() {
        return receiptCount;
    }

    public void setReceiptCount(Integer receiptCount) {
        this.receiptCount = receiptCount;
    }

    public Double getQuotationTotal() {
        return quotationTotal;
    }

    public void setQuotationTotal(Double quotationTotal) {
        this.quotationTotal = quotationTotal;
    }

    public Integer getQuotationCount() {
        return quotationCount;
    }

    public void setQuotationCount(Integer quotationCount) {
        this.quotationCount = quotationCount;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }
}