package com.spring_batch.RestErp.tenant.dto;

public class CompanyRegistry {

    private Long companyId;
    private String companyName;
    private String schemaName;
    private String city;
    private String country;
    private Boolean active;
    private Integer employeeCount;
    private Integer endSalaryMonthDay;

    public CompanyRegistry(Integer endSalaryMonthDay, Integer employeeCount,
                      Boolean active, String country, String city, String schemaName, String companyName,
                      Long companyId) {
        this.endSalaryMonthDay = endSalaryMonthDay;
        this.employeeCount = employeeCount;
        this.active = active;
        this.country = country;
        this.city = city;
        this.schemaName = schemaName;
        this.companyName = companyName;
        this.companyId = companyId;
    }

    public CompanyRegistry() {

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

    @Override
    public String toString() {
        return "CompanyRegistry{" +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", schemaName='" + schemaName + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", active=" + active +
                ", employeeCount=" + employeeCount +
                ", endSalaryMonthDay=" + endSalaryMonthDay +
                '}';
    }
}