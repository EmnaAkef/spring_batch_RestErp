package com.spring_batch.RestErp.finance.dto.source;

import java.time.LocalDateTime;

public class FactAssetSource {

    private Long assetId;
    private Long companyId;
    private String schemaName;

    private String assetType;

    private Double assetValue;
    private Double depreciationAmount;
    private Double percentage;
    private Integer yearsDuration;

    private LocalDateTime assignDate;

    public FactAssetSource() {
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
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

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public Double getAssetValue() {
        return assetValue;
    }

    public void setAssetValue(Double assetValue) {
        this.assetValue = assetValue;
    }

    public Double getDepreciationAmount() {
        return depreciationAmount;
    }

    public void setDepreciationAmount(Double depreciationAmount) {
        this.depreciationAmount = depreciationAmount;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Integer getYearsDuration() {
        return yearsDuration;
    }

    public void setYearsDuration(Integer yearsDuration) {
        this.yearsDuration = yearsDuration;
    }

    public LocalDateTime getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(LocalDateTime assignDate) {
        this.assignDate = assignDate;
    }
}