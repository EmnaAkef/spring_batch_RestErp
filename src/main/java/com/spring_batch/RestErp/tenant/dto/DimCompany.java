package com.spring_batch.RestErp.tenant.dto;

import java.time.LocalDateTime;

public class DimCompany {

    private Integer companyKey;
    private Long companyId;
    private String companyName;
    private String schemaName;
    private String city;
    private String country;
    private Boolean active;
    private Integer employeeCount;
    private Integer endSalaryMonthDay;
    private LocalDateTime effectiveFrom;
    private LocalDateTime effectiveTo;
    private Boolean isCurrent;

    public DimCompany() {
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Integer getEndSalaryMonthDay() {
        return endSalaryMonthDay;
    }

    public void setEndSalaryMonthDay(Integer endSalaryMonthDay) {
        this.endSalaryMonthDay = endSalaryMonthDay;
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

    @Override
    public String toString() {
        return "DimCompany{" +
                "companyKey=" + companyKey +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", schemaName='" + schemaName + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", active=" + active +
                ", employeeCount=" + employeeCount +
                ", endSalaryMonthDay=" + endSalaryMonthDay +
                ", effectiveFrom=" + effectiveFrom +
                ", effectiveTo=" + effectiveTo +
                ", isCurrent=" + isCurrent +
                '}';
    }
}