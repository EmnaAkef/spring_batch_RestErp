package com.spring_batch.RestErp.commun.batch.step;

import com.spring_batch.RestErp.commun.dto.dim.DimCustomer;
import com.spring_batch.RestErp.commun.dto.source.CustomerSource;
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
public class CustomerStepConfig {

    @Bean
    public Step loadDimCustomerStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<CustomerSource> customerReader,
            ItemProcessor<CustomerSource, DimCustomer> customerProcessor,
            ItemWriter<DimCustomer> customerWriter) {

        return new StepBuilder("loadDimCustomerStep", jobRepository)
                .<CustomerSource, DimCustomer>chunk(10, transactionManager)
                .reader(customerReader)
                .processor(customerProcessor)
                .writer(customerWriter)
                .build();
    }
}