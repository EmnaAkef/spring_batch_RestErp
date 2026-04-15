package com.spring_batch.RestErp.sales.batch.step;

import com.spring_batch.RestErp.sales.dto.fact.FactSalesOrder;
import com.spring_batch.RestErp.sales.dto.source.FactSalesOrderSource;
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
public class SalesOrderStepConfig {

    @Bean
    public Step loadFactSalesOrderStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<FactSalesOrderSource> salesOrderReader,
            ItemProcessor<FactSalesOrderSource, FactSalesOrder> salesOrderProcessor,
            ItemWriter<FactSalesOrder> salesOrderWriter) {

        return new StepBuilder("loadFactSalesOrderStep", jobRepository)
                .<FactSalesOrderSource, FactSalesOrder>chunk(10, transactionManager)
                .reader(salesOrderReader)
                .processor(salesOrderProcessor)
                .writer(salesOrderWriter)
                .build();
    }
}