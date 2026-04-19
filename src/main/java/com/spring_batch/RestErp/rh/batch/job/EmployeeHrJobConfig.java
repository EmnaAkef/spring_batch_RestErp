package com.spring_batch.RestErp.rh.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeHrJobConfig {

    @Bean
    public Job loadFactEmployeeHrJob(
            JobRepository jobRepository,
            Step loadFactEmployeeHrStep) {

        return new JobBuilder("loadFactEmployeeHrJob", jobRepository)
                .start(loadFactEmployeeHrStep)
                .build();
    }
}