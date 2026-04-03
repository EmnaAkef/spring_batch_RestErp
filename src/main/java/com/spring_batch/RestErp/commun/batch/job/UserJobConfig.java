package com.spring_batch.RestErp.commun.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserJobConfig {

    @Bean
    public Job loadDimUserJob(
            JobRepository jobRepository,
            Step loadDimUserStep) {

        return new JobBuilder("loadDimUserJob", jobRepository)
                .start(loadDimUserStep)
                .build();
    }
}
