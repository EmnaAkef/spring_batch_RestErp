package com.spring_batch.RestErp.rh.batch.step;

import com.spring_batch.RestErp.rh.dto.fact.FactJobApplication;
import com.spring_batch.RestErp.rh.dto.source.FactJobApplicationSource;
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
public class FactJobApplicationStepConfig {

    @Bean
    public Step loadFactJobApplicationStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<FactJobApplicationSource> factJobApplicationReader,
            ItemProcessor<FactJobApplicationSource, FactJobApplication> factJobApplicationProcessor,
            ItemWriter<FactJobApplication> factJobApplicationWriter) {

        return new StepBuilder("loadFactJobApplicationStep", jobRepository)
                .<FactJobApplicationSource, FactJobApplication>chunk(1000, transactionManager)
                .reader(factJobApplicationReader)
                .processor(factJobApplicationProcessor)
                .writer(factJobApplicationWriter)
                .build();
    }
}