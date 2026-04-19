package com.spring_batch.RestErp.rh.batch.processor;

import com.spring_batch.RestErp.rh.dto.fact.FactEmployeeHr;
import com.spring_batch.RestErp.rh.dto.source.FactEmployeeHrSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.temporal.ChronoUnit;

@Configuration
public class EmployeeHrProcessorConfig {

    @Bean
    public ItemProcessor<FactEmployeeHrSource, FactEmployeeHr> employeeHrProcessor() {
        return source -> {

            if (source == null
                    || source.getFactDate() == null
                    || source.getUserId() == null
                    || source.getCompanyId() == null
                    || source.getDepartmentId() == null) {
                return null;
            }

            FactEmployeeHr target = new FactEmployeeHr();

            target.setFactDate(source.getFactDate());
            target.setDateKey(null);

            target.setUserId(source.getUserId());
            target.setUserKey(null);

            target.setCompanyId(source.getCompanyId());
            target.setCompanyKey(null);

            target.setDepartmentId(source.getDepartmentId());
            target.setDepartmentKey(null);

            target.setEmployeeCount(1);

            target.setIsActiveFlag(Boolean.TRUE.equals(source.getActive()) ? 1 : 0);

            target.setIsEmployeeFlag(isEmployeeType(source.getType()) ? 1 : 0);

            target.setOnboardingFlag(
                    source.getContractStart() != null && source.getContractStart().isEqual(source.getFactDate()) ? 1 : 0
            );

            target.setOffboardingFlag(
                    ("OFFBOARDING".equalsIgnoreCase(source.getType())) ? 1 : 0
            );

            target.setTenureDays(calculateTenureDays(source.getContractStart(), source.getFactDate()));

            target.setBaseSalary(source.getBaseSalary());
            target.setFinalSalary(source.getFinalSalary());
            target.setTaxesAmount(source.getTaxesAmount());
            target.setInsuranceAmount(source.getInsuranceAmount());

            target.setPayrollCount(source.getPayrollCount() != null ? source.getPayrollCount() : 0);
            target.setIsPaidFlag(source.getIsPaidFlag() != null ? source.getIsPaidFlag() : 0);

            return target;
        };
    }

    private boolean isEmployeeType(String type) {
        if (type == null) {
            return false;
        }

        return switch (type.trim().toUpperCase()) {
            case "EMPLOYEE" -> true;
            case "CONTRACTOR", "OFFBOARDING", "COMPANY_OWNER" -> false;
            default -> false;
        };
    }

    private Integer calculateTenureDays(java.time.LocalDate contractStart, java.time.LocalDate factDate) {
        if (contractStart == null || factDate == null) {
            return null;
        }

        if (contractStart.isAfter(factDate)) {
            return 0;
        }

        return (int) ChronoUnit.DAYS.between(contractStart, factDate);
    }
}