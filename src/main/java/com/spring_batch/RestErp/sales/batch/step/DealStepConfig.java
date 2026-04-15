package com.spring_batch.RestErp.sales.batch.step;

import com.spring_batch.RestErp.sales.dto.fact.FactDeal;
import com.spring_batch.RestErp.sales.dto.source.FactDealSource;
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
public class DealStepConfig {

    @Bean
    public Step loadFactDealStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<FactDealSource> dealReader,
            ItemProcessor<FactDealSource, FactDeal> dealProcessor,
            ItemWriter<FactDeal> dealWriter) {

        return new StepBuilder("loadFactDealStep", jobRepository)
                .<FactDealSource, FactDeal>chunk(10, transactionManager)
                .reader(dealReader)
                .processor(dealProcessor)
                .writer(dealWriter)
                .build();
    }
}