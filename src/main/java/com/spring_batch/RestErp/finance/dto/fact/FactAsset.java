package com.spring_batch.RestErp.finance.dto.fact;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FactAsset {

    private Integer assetFactKey;
    private Long assetId;

    private Integer dateKey;
    private Integer companyKey;
    private Integer assetTypeKey;

    private BigDecimal assetValue;
    private BigDecimal depreciationAmount;
    private BigDecimal netBookValue;
    private BigDecimal percentage;
    private Integer yearsDuration;
    private Integer assetCount;

    // Champs techniques ETL
    private Long companyId;
    private String assetType;
    private LocalDateTime assignDate;

    public FactAsset() {
    }

    public Integer getAssetFactKey() {
        return assetFactKey;
    }

    public void setAssetFactKey(Integer assetFactKey) {
        this.assetFactKey = assetFactKey;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
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

    public Integer getAssetTypeKey() {
        return assetTypeKey;
    }

    public void setAssetTypeKey(Integer assetTypeKey) {
        this.assetTypeKey = assetTypeKey;
    }

    public BigDecimal getAssetValue() {
        return assetValue;
    }

    public void setAssetValue(BigDecimal assetValue) {
        this.assetValue = assetValue;
    }

    public BigDecimal getDepreciationAmount() {
        return depreciationAmount;
    }

    public void setDepreciationAmount(BigDecimal depreciationAmount) {
        this.depreciationAmount = depreciationAmount;
    }

    public BigDecimal getNetBookValue() {
        return netBookValue;
    }

    public void setNetBookValue(BigDecimal netBookValue) {
        this.netBookValue = netBookValue;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public Integer getYearsDuration() {
        return yearsDuration;
    }

    public void setYearsDuration(Integer yearsDuration) {
        this.yearsDuration = yearsDuration;
    }

    public Integer getAssetCount() {
        return assetCount;
    }

    public void setAssetCount(Integer assetCount) {
        this.assetCount = assetCount;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public LocalDateTime getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(LocalDateTime assignDate) {
        this.assignDate = assignDate;
    }
}