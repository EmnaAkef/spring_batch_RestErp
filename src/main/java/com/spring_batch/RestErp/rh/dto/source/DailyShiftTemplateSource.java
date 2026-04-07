package com.spring_batch.RestErp.rh.dto.source;

public class DailyShiftTemplateSource {

    private Long dailyShiftId;
    private Long companyId;
    private String schemaName;

    private Long weeklyTemplateId;

    private Integer dayOfWeek;
    private Boolean approve;
    private Integer generalCheckinState;
    private Integer generalCheckoutState;
    private Double workingHours;

    public DailyShiftTemplateSource() {
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

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public Long getWeeklyTemplateId() {
        return weeklyTemplateId;
    }

    public void setWeeklyTemplateId(Long weeklyTemplateId) {
        this.weeklyTemplateId = weeklyTemplateId;
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

    public Integer getGeneralCheckinState() {
        return generalCheckinState;
    }

    public void setGeneralCheckinState(Integer generalCheckinState) {
        this.generalCheckinState = generalCheckinState;
    }

    public Integer getGeneralCheckoutState() {
        return generalCheckoutState;
    }

    public void setGeneralCheckoutState(Integer generalCheckoutState) {
        this.generalCheckoutState = generalCheckoutState;
    }

    public Double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Double workingHours) {
        this.workingHours = workingHours;
    }
}