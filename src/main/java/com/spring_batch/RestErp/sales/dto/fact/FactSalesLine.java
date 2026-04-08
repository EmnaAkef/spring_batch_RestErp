package com.spring_batch.RestErp.sales.dto.fact;

import java.time.LocalDate;

public class FactSalesLine {

    private Long salesLineId;

    private Long companyId;
    private Integer companyKey;

    private Integer dateKey;

    private Long productId;
    private Integer productKey;

    private Long quotationId;
    private Long invoiceId;
    private Long sellOrderId;

    private Double quantity;
    private Double unitPrice;
    private Double discount;
    private Double subtotal;
    private Double tax;
    private Double lineRevenue;
    private java.time.LocalDate salesDate;

    public FactSalesLine() {
    }

    public Long getSalesLineId() {
        return salesLineId;
    }

    public void setSalesLineId(Long salesLineId) {
        this.salesLineId = salesLineId;
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

    public Integer getDateKey() {
        return dateKey;
    }

    public void setDateKey(Integer dateKey) {
        this.dateKey = dateKey;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getProductKey() {
        return productKey;
    }

    public void setProductKey(Integer productKey) {
        this.productKey = productKey;
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

    public Long getSellOrderId() {
        return sellOrderId;
    }

    public void setSellOrderId(Long sellOrderId) {
        this.sellOrderId = sellOrderId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getLineRevenue() {
        return lineRevenue;
    }

    public void setLineRevenue(Double lineRevenue) {
        this.lineRevenue = lineRevenue;
    }

    public LocalDate getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(LocalDate salesDate) {
        this.salesDate = salesDate;
    }
}