package com.spring_batch.RestErp.finance.dto.source;

import java.time.LocalDateTime;

public class CashMovementSource {

    private Long cashId;
    private Long companyId;
    private String schemaName;

    private Long chartId;
    private Long userId;

    private Double debit;
    private Double credit;

    private Double debitReconciliation;
    private Double creditReconciliation;

    private Double openingBalance;

    private LocalDateTime timestamp;

    public CashMovementSource() {
    }

    public Long getCashId() {
        return cashId;
    }

    public void setCashId(Long cashId) {
        this.cashId = cashId;
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

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getDebit() {
        return debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Double getDebitReconciliation() {
        return debitReconciliation;
    }

    public void setDebitReconciliation(Double debitReconciliation) {
        this.debitReconciliation = debitReconciliation;
    }

    public Double getCreditReconciliation() {
        return creditReconciliation;
    }

    public void setCreditReconciliation(Double creditReconciliation) {
        this.creditReconciliation = creditReconciliation;
    }

    public Double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}