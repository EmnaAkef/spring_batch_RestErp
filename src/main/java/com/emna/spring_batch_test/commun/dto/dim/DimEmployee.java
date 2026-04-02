package com.emna.spring_batch_test.commun.dto.dim;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DimEmployee {

    private Long tenantKey;
    private Long employeeIdSource;
    private String employeeCode;
    private String fullName;
    private String departmentName;
    private LocalDate hireDate;
    private Boolean active;
    private LocalDateTime effectiveFrom;

    public Long getTenantKey() {
        return tenantKey;
    }

    public void setTenantKey(Long tenantKey) {
        this.tenantKey = tenantKey;
    }

    public Long getEmployeeIdSource() {
        return employeeIdSource;
    }

    public void setEmployeeIdSource(Long employeeIdSource) {
        this.employeeIdSource = employeeIdSource;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDateTime effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }
}