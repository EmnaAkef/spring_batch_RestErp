package com.spring_batch.RestErp.rh.batch.step;

import com.spring_batch.RestErp.rh.dto.fact.FactEmployeeHr;
import com.spring_batch.RestErp.rh.dto.source.FactEmployeeHrSource;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class EmployeeHrStepConfig {

    @Bean
    public Step loadFactEmployeeHrStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<FactEmployeeHrSource> employeeHrReader,
            ItemProcessor<FactEmployeeHrSource, FactEmployeeHr> employeeHrProcessor,
            ItemWriter<FactEmployeeHr> employeeHrWriter) {

        return new StepBuilder("loadFactEmployeeHrStep", jobRepository)
                .<FactEmployeeHrSource, FactEmployeeHr>chunk(10, transactionManager)
                .reader(employeeHrReader)
                .processor(employeeHrProcessor)
                .writer(employeeHrWriter)
                .build();
    }
}