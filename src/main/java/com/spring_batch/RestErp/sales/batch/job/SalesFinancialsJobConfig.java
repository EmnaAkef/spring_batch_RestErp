package com.spring_batch.RestErp.sales.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SalesFinancialsJobConfig {

    @Bean
    public Job loadFactSalesFinancialsJob(
            JobRepository jobRepository,
            Step loadFactSalesFinancialsStep) {

        return new JobBuilder("loadFactSalesFinancialsJob", jobRepository)
                .start(loadFactSalesFinancialsStep)
                .build();
    }
}