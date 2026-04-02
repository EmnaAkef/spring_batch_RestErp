package com.spring_batch.RestErp.commun.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DepartmentJobConfig {

    @Bean
    public Job loadDimDepartmentJob(
            JobRepository jobRepository,
            Step loadDimDepartmentStep) {

        return new JobBuilder("loadDimDepartmentJob", jobRepository)
                .start(loadDimDepartmentStep)
                .build();
    }
}
