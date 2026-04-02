package com.emna.spring_batch_test.tenant.batch;

import java.time.LocalDateTime;

import com.emna.spring_batch_test.tenant.dto.CompanyRegistry;
import com.emna.spring_batch_test.tenant.dto.DimCompany;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompanyProcessorConfig {

    @Bean
    public ItemProcessor<CompanyRegistry, DimCompany> tenantProcessor() {
        return source -> {
            DimCompany target = new DimCompany();
            target.setCompanyId(source.getCompanyId());
            target.setCompanyName(source.getCompanyName());
            target.setCity(source.getCity());
            target.setCountry(source.getCountry());
            target.setEmployeeCount(source.getEmployeeCount());
            target.setEndSalaryMonthDay(source.getEndSalaryMonthDay());
            target.setSchemaName(source.getSchemaName());
            target.setActive(source.getActive());

            target.setEffectiveFrom(LocalDateTime.now());
            target.setEffectiveTo(null);
            target.setIsCurrent(true);

            return target;
        };
    }
}