package com.spring_batch.RestErp.commun.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerJobConfig {

    @Bean
    public Job loadDimCustomerJob(
            JobRepository jobRepository,
            Step loadDimCustomerStep) {

        return new JobBuilder("loadDimCustomerJob", jobRepository)
                .start(loadDimCustomerStep)
                .build();
    }
}
