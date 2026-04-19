package com.spring_batch.RestErp.rh.dto.source;

import java.time.LocalDate;

public class FactEmployeeHrSource {

    private LocalDate factDate;

    private Long userId;
    private Long companyId;
    private Long departmentId;

    private Boolean active;
    private String type;

    private LocalDate contractStart;
    private LocalDate contractEnd;

    private Double baseSalary;
    private Double finalSalary;
    private Double taxesAmount;
    private Double insuranceAmount;

    private Integer payrollCount;
    private Integer isPaidFlag;

    public FactEmployeeHrSource() {
    }

    public LocalDate getFactDate() {
        return factDate;
    }

    public void setFactDate(LocalDate factDate) {
        this.factDate = factDate;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getContractStart() {
        return contractStart;
    }

    public void setContractStart(LocalDate contractStart) {
        this.contractStart = contractStart;
    }

    public LocalDate getContractEnd() {
        return contractEnd;
    }

    public void setContractEnd(LocalDate contractEnd) {
        this.contractEnd = contractEnd;
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