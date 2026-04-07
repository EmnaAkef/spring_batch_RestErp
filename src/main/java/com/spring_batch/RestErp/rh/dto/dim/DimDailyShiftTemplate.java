package com.spring_batch.RestErp.rh.dto.dim;

import java.time.LocalDateTime;

public class DimDailyShiftTemplate {

    private Long dailyShiftId;
    private Long companyId;
    private Integer companyKey;

    private Long weeklyTemplateId;
    private Integer weeklyTemplateKey;

    private Integer dayOfWeek;
    private Boolean approve;
    private String generalCheckinState;
    private String generalCheckoutState;
    private Double workingHours;

    private LocalDateTime effectiveFrom;
    private LocalDateTime effectiveTo;
    private Boolean isCurrent;

    public DimDailyShiftTemplate() {
    }

    public Long getDailyShiftId() {
        return dailyShiftId;
    }

    public void setDailyShiftId(Long dailyShiftId) {
        this.dailyShiftId = dailyShiftId;
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

    public Long getWeeklyTemplateId() {
        return weeklyTemplateId;
    }

    public void setWeeklyTemplateId(Long weeklyTemplateId) {
        this.weeklyTemplateId = weeklyTemplateId;
    }

    public Integer getWeeklyTemplateKey() {
        return weeklyTemplateKey;
    }

    public void setWeeklyTemplateKey(Integer weeklyTemplateKey) {
        this.weeklyTemplateKey = weeklyTemplateKey;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Boolean getApprove() {
        return approve;
    }

    public void setApprove(Boolean approve) {
        this.approve = approve;
    }

    public String getGeneralCheckinState() {
        return generalCheckinState;
    }

    public void setGeneralCheckinState(String generalCheckinState) {
        this.generalCheckinState = generalCheckinState;
    }

    public String getGeneralCheckoutState() {
        return generalCheckoutState;
    }

    public void setGeneralCheckoutState(String generalCheckoutState) {
        this.generalCheckoutState = generalCheckoutState;
    }

    public Double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Double workingHours) {
        this.workingHours = workingHours;
    }

    public LocalDateTime getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDateTime effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public LocalDateTime getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(LocalDateTime effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }
}