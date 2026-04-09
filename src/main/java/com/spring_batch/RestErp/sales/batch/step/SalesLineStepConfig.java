package com.spring_batch.RestErp.sales.batch.step;

import com.spring_batch.RestErp.sales.dto.fact.FactSalesLine;
import com.spring_batch.RestErp.sales.dto.source.FactSalesLineSource;
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
public class SalesLineStepConfig {

    @Bean
    public Step loadFactSalesLineStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<FactSalesLineSource> salesLineReader,
            ItemProcessor<FactSalesLineSource, FactSalesLine> salesLineProcessor,
            ItemWriter<FactSalesLine> salesLineWriter) {

        return new StepBuilder("loadFactSalesLineStep", jobRepository)
                .<FactSalesLineSource, FactSalesLine>chunk(10, transactionManager)
                .reader(salesLineReader)
                .processor(salesLineProcessor)
                .writer(salesLineWriter)
                .build();
    }
}