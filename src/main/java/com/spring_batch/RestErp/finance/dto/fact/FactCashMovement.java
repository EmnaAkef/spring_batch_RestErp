package com.spring_batch.RestErp.finance.dto.fact;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FactCashMovement {

    private Integer cashFactKey;
    private Long cashId;

    private Integer dateKey;
    private Integer chartAccountKey;
    private Integer companyKey;
    private Integer userKey;

    private BigDecimal debit;
    private BigDecimal credit;
    private BigDecimal netAmount;

    private BigDecimal openingBalance;
    private BigDecimal debitReconciliation;
    private BigDecimal creditReconciliation;
    private BigDecimal reconciliationGap;

    // Champs techniques ETL
    private Long companyId;
    private Long chartId;
    private Long userId;
    private LocalDateTime timestamp;

    public FactCashMovement() {
    }

    public Integer getCashFactKey() {
        return cashFactKey;
    }

    public void setCashFactKey(Integer cashFactKey) {
        this.cashFactKey = cashFactKey;
    }

    public Long getCashId() {
        return cashId;
    }

    public void setCashId(Long cashId) {
        this.cashId = cashId;
    }

    public Integer getDateKey() {
        return dateKey;
    }

    public void setDateKey(Integer dateKey) {
        this.dateKey = dateKey;
    }

    public Integer getChartAccountKey() {
        return chartAccountKey;
    }

    public void setChartAccountKey(Integer chartAccountKey) {
        this.chartAccountKey = chartAccountKey;
    }

    public Integer getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(Integer companyKey) {
        this.companyKey = companyKey;
    }

    public Integer getUserKey() {
        return userKey;
    }

    public void setUserKey(Integer userKey) {
        this.userKey = userKey;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(BigDecimal openingBalance) {
        this.openingBalance = openingBalance;
    }

    public BigDecimal getDebitReconciliation() {
        return debitReconciliation;
    }

    public void setDebitReconciliation(BigDecimal debitReconciliation) {
        this.debitReconciliation = debitReconciliation;
    }

    public BigDecimal getCreditReconciliation() {
        return creditReconciliation;
    }

    public void setCreditReconciliation(BigDecimal creditReconciliation) {
        this.creditReconciliation = creditReconciliation;
    }

    public BigDecimal getReconciliationGap() {
        return reconciliationGap;
    }

    public void setReconciliationGap(BigDecimal reconciliationGap) {
        this.reconciliationGap = reconciliationGap;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}