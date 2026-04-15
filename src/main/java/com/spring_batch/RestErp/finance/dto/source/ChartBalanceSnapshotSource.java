package com.spring_batch.RestErp.finance.dto.source;

import java.time.LocalDateTime;

public class ChartBalanceSnapshotSource {

    private Long chartBalanceId;
    private Long companyId;
    private String schemaName;

    private Long chartId;
    private LocalDateTime timestamp;

    private Double closeBalanceCredit;
    private Double closeBalanceDebit;
    private Double openBalanceCredit;
    private Double openBalanceDebit;
    private Double totalMovementCredit;
    private Double totalMovementDebit;

    public ChartBalanceSnapshotSource() {
    }

    public Long getChartBalanceId() {
        return chartBalanceId;
    }

    public void setChartBalanceId(Long chartBalanceId) {
        this.chartBalanceId = chartBalanceId;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Double getCloseBalanceCredit() {
        return closeBalanceCredit;
    }

    public void setCloseBalanceCredit(Double closeBalanceCredit) {
        this.closeBalanceCredit = closeBalanceCredit;
    }

    public Double getCloseBalanceDebit() {
        return closeBalanceDebit;
    }

    public void setCloseBalanceDebit(Double closeBalanceDebit) {
        this.closeBalanceDebit = closeBalanceDebit;
    }

    public Double getOpenBalanceCredit() {
        return openBalanceCredit;
    }

    public void setOpenBalanceCredit(Double openBalanceCredit) {
        this.openBalanceCredit = openBalanceCredit;
    }

    public Double getOpenBalanceDebit() {
        return openBalanceDebit;
    }

    public void setOpenBalanceDebit(Double openBalanceDebit) {
        this.openBalanceDebit = openBalanceDebit;
    }

    public Double getTotalMovementCredit() {
        return totalMovementCredit;
    }

    public void setTotalMovementCredit(Double totalMovementCredit) {
        this.totalMovementCredit = totalMovementCredit;
    }

    public Double getTotalMovementDebit() {
        return totalMovementDebit;
    }

    public void setTotalMovementDebit(Double totalMovementDebit) {
        this.totalMovementDebit = totalMovementDebit;
    }
}