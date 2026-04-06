package com.spring_batch.RestErp.finance.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VendorJobConfig {

    @Bean
    public Job loadDimVendorJob(
            JobRepository jobRepository,
            Step loadDimVendorStep) {

        return new JobBuilder("loadDimVendorJob", jobRepository)
                .start(loadDimVendorStep)
                .build();
    }
}
