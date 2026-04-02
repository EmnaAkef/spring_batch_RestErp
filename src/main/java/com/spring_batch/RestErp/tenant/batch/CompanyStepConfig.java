package com.spring_batch.RestErp.tenant.batch;

import com.spring_batch.RestErp.tenant.dto.CompanyRegistry;
import com.spring_batch.RestErp.tenant.dto.DimCompany;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class CompanyStepConfig {

    @Bean
    public Step loadDimCompanyStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            JdbcCursorItemReader<CompanyRegistry> companyReader,
            ItemProcessor<CompanyRegistry, DimCompany> companyProcessor,
            ItemWriter<DimCompany> companyWriter) {

        return new StepBuilder("loadDimCompanyStep", jobRepository)
                .<CompanyRegistry, DimCompany>chunk(10, transactionManager)
                .reader(companyReader)
                .processor(companyProcessor)
                .writer(companyWriter)
                .build();
    }
}