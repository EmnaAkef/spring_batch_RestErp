package com.spring_batch.RestErp.sales.batch.step;

import com.spring_batch.RestErp.sales.dto.fact.FactSalesFinancials;
import com.spring_batch.RestErp.sales.dto.source.FactSalesFinancialsSource;
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
public class SalesFinancialsStepConfig {

    @Bean
    public Step loadFactSalesFinancialsStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<FactSalesFinancialsSource> salesFinancialsReader,
            ItemProcessor<FactSalesFinancialsSource, FactSalesFinancials> salesFinancialsProcessor,
            ItemWriter<FactSalesFinancials> salesFinancialsWriter) {

        return new StepBuilder("loadFactSalesFinancialsStep", jobRepository)
                .<FactSalesFinancialsSource, FactSalesFinancials>chunk(10, transactionManager)
                .reader(salesFinancialsReader)
                .processor(salesFinancialsProcessor)
                .writer(salesFinancialsWriter)
                .build();
    }
}