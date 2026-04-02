package com.spring_batch.RestErp.tenant.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompanyJobConfig {

    @Bean
    public Job loadDimCompanyJob(
            JobRepository jobRepository,
            Step loadDimCompanyStep) {

        return new JobBuilder("loadDimCompanyJob", jobRepository)
                .start(loadDimCompanyStep)
                .build();
    }
}