package com.spring_batch.RestErp.finance.batch.step;

import com.spring_batch.RestErp.finance.dto.fact.FactBill;
import com.spring_batch.RestErp.finance.dto.source.FactBillSource;
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
public class FactBillStepConfig {

    @Bean
    public Step loadFactBillStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<FactBillSource> factBillReader,
            ItemProcessor<FactBillSource, FactBill> factBillProcessor,
            ItemWriter<FactBill> factBillWriter) {

        return new StepBuilder("loadFactBillStep", jobRepository)
                .<FactBillSource, FactBill>chunk(1000, transactionManager)
                .reader(factBillReader)
                .processor(factBillProcessor)
                .writer(factBillWriter)
                .build();
    }
}