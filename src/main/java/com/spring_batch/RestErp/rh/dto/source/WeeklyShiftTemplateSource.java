package com.spring_batch.RestErp.rh.dto.source;

import java.time.LocalDateTime;

public class WeeklyShiftTemplateSource {

    private Long weeklyTemplateId;
    private Long companyId;
    private String schemaName;

    private Long creatorId;
    private String creatorName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String templateName;
    private Double totalWorkHours;
    private String type;

    public WeeklyShiftTemplateSource() {
    }

    public Long getWeeklyTemplateId() {
        return weeklyTemplateId;
    }

    public void setWeeklyTemplateId(Long weeklyTemplateId) {
        this.weeklyTemplateId = weeklyTemplateId;
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

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Double getTotalWorkHours() {
        return totalWorkHours;
    }

    public void setTotalWorkHours(Double totalWorkHours) {
        this.totalWorkHours = totalWorkHours;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}