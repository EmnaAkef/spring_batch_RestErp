package com.spring_batch.RestErp.rh.dto.fact;

import java.time.LocalDate;

public class FactAbsenceMonthly {

    private LocalDate monthDate;
    private Integer monthDateKey;

    private Long companyId;
    private Integer companyKey;

    private Long departmentId;
    private Integer departmentKey;

    private Integer scheduledShiftsCount;
    private Integer absentShiftsCount;
    private Double absenceRatePct;

    public FactAbsenceMonthly() {
    }

    public LocalDate getMonthDate() {
        return monthDate;
    }

    public void setMonthDate(LocalDate monthDate) {
        this.monthDate = monthDate;
    }

    public Integer getMonthDateKey() {
        return monthDateKey;
    }

    public void setMonthDateKey(Integer monthDateKey) {
        this.monthDateKey = monthDateKey;
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

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getDepartmentKey() {
        return departmentKey;
    }

    public void setDepartmentKey(Integer departmentKey) {
        this.departmentKey = departmentKey;
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