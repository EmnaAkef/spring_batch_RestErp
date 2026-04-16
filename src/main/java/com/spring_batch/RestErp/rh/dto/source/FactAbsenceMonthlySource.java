package com.spring_batch.RestErp.rh.dto.source;

import java.time.LocalDate;

public class FactAbsenceMonthlySource {

    private Long companyId;
    private Long departmentId;

    private LocalDate monthDate;

    private Integer scheduledShiftsCount;
    private Integer absentShiftsCount;
    private Double absenceRatePct;

    public FactAbsenceMonthlySource() {
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public LocalDate getMonthDate() {
        return monthDate;
    }

    public void setMonthDate(LocalDate monthDate) {
        this.monthDate = monthDate;
    }

    public Integer getScheduledShiftsCount() {
        return scheduledShiftsCount;
    }

    public void setScheduledShiftsCount(Integer scheduledShiftsCount) {
        this.scheduledShiftsCount = scheduledShiftsCount;
    }

    public Integer getAbsentShiftsCount() {
        return absentShiftsCount;
    }

    public void setAbsentShiftsCount(Integer absentShiftsCount) {
        this.absentShiftsCount = absentShiftsCount;
    }

    public Double getAbsenceRatePct() {
        return absenceRatePct;
    }

    public void setAbsenceRatePct(Double absenceRatePct) {
        this.absenceRatePct = absenceRatePct;
    }
}