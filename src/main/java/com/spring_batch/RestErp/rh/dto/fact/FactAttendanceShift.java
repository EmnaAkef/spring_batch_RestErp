package com.spring_batch.RestErp.rh.dto.fact;

import java.time.LocalDate;

public class FactAttendanceShift {

    private Integer dateKey;
    private LocalDate attendanceDate;

    private Integer userKey;
    private Long userId;

    private Integer companyKey;
    private Long companyId;

    private Integer departmentKey;
    private Long departmentId;

    private Integer workstatusKey;
    private Long workstatusId;

    private Integer shiftTemplateKey;
    private Long shiftTemplateId;

    private Integer scheduledShiftCount;
    private Integer presentShiftCount;
    private Integer absentShiftCount;
    private Integer lateCheckinCount;

    private Double workingHoursPlanned;
    private Double workingHoursActual;
    private Double overtimeHours;

    public FactAttendanceShift() {
    }

    public Integer getDateKey() {
        return dateKey;
    }

    public void setDateKey(Integer dateKey) {
        this.dateKey = dateKey;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public Integer getUserKey() {
        return userKey;
    }

    public void setUserKey(Integer userKey) {
        this.userKey = userKey;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(Integer companyKey) {
        this.companyKey = companyKey;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getDepartmentKey() {
        return departmentKey;
    }

    public void setDepartmentKey(Integer departmentKey) {
        this.departmentKey = departmentKey;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getWorkstatusKey() {
        return workstatusKey;
    }

    public void setWorkstatusKey(Integer workstatusKey) {
        this.workstatusKey = workstatusKey;
    }

    public Long getWorkstatusId() {
        return workstatusId;
    }

    public void setWorkstatusId(Long workstatusId) {
        this.workstatusId = workstatusId;
    }

    public Integer getShiftTemplateKey() {
        return shiftTemplateKey;
    }

    public void setShiftTemplateKey(Integer shiftTemplateKey) {
        this.shiftTemplateKey = shiftTemplateKey;
    }

    public Long getShiftTemplateId() {
        return shiftTemplateId;
    }

    public void setShiftTemplateId(Long shiftTemplateId) {
        this.shiftTemplateId = shiftTemplateId;
    }

    public Integer getScheduledShiftCount() {
        return scheduledShiftCount;
    }

    public void setScheduledShiftCount(Integer scheduledShiftCount) {
        this.scheduledShiftCount = scheduledShiftCount;
    }

    public Integer getPresentShiftCount() {
        return presentShiftCount;
    }

    public void setPresentShiftCount(Integer presentShiftCount) {
        this.presentShiftCount = presentShiftCount;
    }

    public Integer getAbsentShiftCount() {
        return absentShiftCount;
    }

    public void setAbsentShiftCount(Integer absentShiftCount) {
        this.absentShiftCount = absentShiftCount;
    }

    public Integer getLateCheckinCount() {
        return lateCheckinCount;
    }

    public void setLateCheckinCount(Integer lateCheckinCount) {
        this.lateCheckinCount = lateCheckinCount;
    }

    public Double getWorkingHoursPlanned() {
        return workingHoursPlanned;
    }

    public void setWorkingHoursPlanned(Double workingHoursPlanned) {
        this.workingHoursPlanned = workingHoursPlanned;
    }

    public Double getWorkingHoursActual() {
        return workingHoursActual;
    }

    public void setWorkingHoursActual(Double workingHoursActual) {
        this.workingHoursActual = workingHoursActual;
    }

    public Double getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(Double overtimeHours) {
        this.overtimeHours = overtimeHours;
    }
}