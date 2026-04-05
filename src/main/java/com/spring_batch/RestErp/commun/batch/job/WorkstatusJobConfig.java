package com.spring_batch.RestErp.commun.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkstatusJobConfig {

    @Bean
    public Job loadDimWorkstatusJob(
            JobRepository jobRepository,
            Step loadDimWorkstatusStep) {

        return new JobBuilder("loadDimWorkstatusJob", jobRepository)
                .start(loadDimWorkstatusStep)
                .build();
    }
}