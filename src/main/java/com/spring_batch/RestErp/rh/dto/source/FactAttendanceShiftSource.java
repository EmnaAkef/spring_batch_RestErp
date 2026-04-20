package com.spring_batch.RestErp.rh.dto.source;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class FactAttendanceShiftSource {

    private LocalDate attendanceDate;

    private Long userId;
    private Long companyId;
    private Long departmentId;

    private Long shiftTemplateId;
    private Long workstatusId;

    private Integer checkInState;
    private Integer checkOutState;

    private LocalTime checkInTime;
    private LocalTime checkOutTime;

    private Double workingHoursPlanned;
    private Double workingHoursActual;
    private Double overtimeHours;

    public FactAttendanceShiftSource() {
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getShiftTemplateId() {
        return shiftTemplateId;
    }

    public void setShiftTemplateId(Long shiftTemplateId) {
        this.shiftTemplateId = shiftTemplateId;
    }

    public Long getWorkstatusId() {
        return workstatusId;
    }

    public void setWorkstatusId(Long workstatusId) {
        this.workstatusId = workstatusId;
    }

    public Integer getCheckInState() {
        return checkInState;
    }

    public void setCheckInState(Integer checkInState) {
        this.checkInState = checkInState;
    }

    public Integer getCheckOutState() {
        return checkOutState;
    }

    public void setCheckOutState(Integer checkOutState) {
        this.checkOutState = checkOutState;
    }

    public LocalTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalTime checkOutTime) {
        this.checkOutTime = checkOutTime;
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