package com.spring_batch.RestErp.rh.dto.fact;

import java.time.LocalDate;

public class FactEmployeeHr {

    private Integer dateKey;
    private LocalDate factDate;

    private Integer userKey;
    private Long userId;

    private Integer companyKey;
    private Long companyId;

    private Integer departmentKey;
    private Long departmentId;

    private Integer employeeCount;
    private Integer isActiveFlag;
    private Integer isEmployeeFlag;
    private Integer onboardingFlag;
    private Integer offboardingFlag;

    private Integer tenureDays;

    private Double baseSalary;
    private Double finalSalary;
    private Double taxesAmount;
    private Double insuranceAmount;

    private Integer payrollCount;
    private Integer isPaidFlag;

    public FactEmployeeHr() {
    }

    public Integer getDateKey() {
        return dateKey;
    }

    public void setDateKey(Integer dateKey) {
        this.dateKey = dateKey;
    }

    public LocalDate getFactDate() {
        return factDate;
    }

    public void setFactDate(LocalDate factDate) {
        this.factDate = factDate;
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

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Integer getIsActiveFlag() {
        return isActiveFlag;
    }

    public void setIsActiveFlag(Integer isActiveFlag) {
        this.isActiveFlag = isActiveFlag;
    }

    public Integer getIsEmployeeFlag() {
        return isEmployeeFlag;
    }

    public void setIsEmployeeFlag(Integer isEmployeeFlag) {
        this.isEmployeeFlag = isEmployeeFlag;
    }

    public Integer getOnboardingFlag() {
        return onboardingFlag;
    }

    public void setOnboardingFlag(Integer onboardingFlag) {
        this.onboardingFlag = onboardingFlag;
    }

    public Integer getOffboardingFlag() {
        return offboardingFlag;
    }

    public void setOffboardingFlag(Integer offboardingFlag) {
        this.offboardingFlag = offboardingFlag;
    }

    public Integer getTenureDays() {
        return tenureDays;
    }

    public void setTenureDays(Integer tenureDays) {
        this.tenureDays = tenureDays;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Double getFinalSalary() {
        return finalSalary;
    }

    public void setFinalSalary(Double finalSalary) {
        this.finalSalary = finalSalary;
    }

    public Double getTaxesAmount() {
        return taxesAmount;
    }

    public void setTaxesAmount(Double taxesAmount) {
        this.taxesAmount = taxesAmount;
    }

    public Double getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(Double insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public Integer getPayrollCount() {
        return payrollCount;
    }

    public void setPayrollCount(Integer payrollCount) {
        this.payrollCount = payrollCount;
    }

    public Integer getIsPaidFlag() {
        return isPaidFlag;
    }

    public void setIsPaidFlag(Integer isPaidFlag) {
        this.isPaidFlag = isPaidFlag;
    }
}