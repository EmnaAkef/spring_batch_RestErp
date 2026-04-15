package com.spring_batch.RestErp.finance.dto.fact;

public class FactChartBalanceSnapshot {

    private Integer chartSnapshotFactKey;

    private Integer chartAccountKey;
    private Integer dateKey;
    private Integer companyKey;

    private Double closeBalanceDebit;
    private Double closeBalanceCredit;
    private Double openBalanceDebit;
    private Double openBalanceCredit;
    private Double totalMovementDebit;
    private Double totalMovementCredit;

    public FactChartBalanceSnapshot() {
    }

    public Integer getChartSnapshotFactKey() {
        return chartSnapshotFactKey;
    }

    public void setChartSnapshotFactKey(Integer chartSnapshotFactKey) {
        this.chartSnapshotFactKey = chartSnapshotFactKey;
    }

    public Integer getChartAccountKey() {
        return chartAccountKey;
    }

    public void setChartAccountKey(Integer chartAccountKey) {
        this.chartAccountKey = chartAccountKey;
    }

    public Integer getDateKey() {
        return dateKey;
    }

    public void setDateKey(Integer dateKey) {
        this.dateKey = dateKey;
    }

    public Integer getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(Integer companyKey) {
        this.companyKey = companyKey;
    }

    public Double getCloseBalanceDebit() {
        return closeBalanceDebit;
    }

    public void setCloseBalanceDebit(Double closeBalanceDebit) {
        this.closeBalanceDebit = closeBalanceDebit;
    }

    public Double getCloseBalanceCredit() {
        return closeBalanceCredit;
    }

    public void setCloseBalanceCredit(Double closeBalanceCredit) {
        this.closeBalanceCredit = closeBalanceCredit;
    }

    public Double getOpenBalanceDebit() {
        return openBalanceDebit;
    }

    public void setOpenBalanceDebit(Double openBalanceDebit) {
        this.openBalanceDebit = openBalanceDebit;
    }

    public Double getOpenBalanceCredit() {
        return openBalanceCredit;
    }

    public void setOpenBalanceCredit(Double openBalanceCredit) {
        this.openBalanceCredit = openBalanceCredit;
    }

    public Double getTotalMovementDebit() {
        return totalMovementDebit;
    }

    public void setTotalMovementDebit(Double totalMovementDebit) {
        this.totalMovementDebit = totalMovementDebit;
    }

    public Double getTotalMovementCredit() {
        return totalMovementCredit;
    }

    public void setTotalMovementCredit(Double totalMovementCredit) {
        this.totalMovementCredit = totalMovementCredit;
    }
}