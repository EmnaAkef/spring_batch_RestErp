package com.spring_batch.RestErp.finance.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactBillJobConfig {

    @Bean
    public Job loadFactBillJob(
            JobRepository jobRepository,
            @Qualifier("loadFactBillStep") Step loadFactBillStep) {

        return new JobBuilder("loadFactBillJob", jobRepository)
                .start(loadFactBillStep)
                .build();
    }
}