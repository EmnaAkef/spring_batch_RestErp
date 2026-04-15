package com.spring_batch.RestErp.sales.dto.fact;

import java.time.LocalDate;

public class FactSalesOrder {

    private Long sellOrderId;

    private Long companyId;
    private Integer companyKey;

    private LocalDate orderDate;
    private Integer dateKey;

    private Long customerId;
    private Integer customerKey;

    private Long quotationId;
    private Long invoiceId;

    private Integer soCount;
    private String status;

    public FactSalesOrder() {
    }

    public Long getSellOrderId() {
        return sellOrderId;
    }

    public void setSellOrderId(Long sellOrderId) {
        this.sellOrderId = sellOrderId;
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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
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

    public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getSoCount() {
        return soCount;
    }

    public void setSoCount(Integer soCount) {
        this.soCount = soCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}