package com.spring_batch.RestErp.rh.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactJobApplicationJobConfig {

    @Bean
    public Job loadFactJobApplicationJob(
            JobRepository jobRepository,
            @Qualifier("loadFactJobApplicationStep") Step loadFactJobApplicationStep) {

        return new JobBuilder("loadFactJobApplicationJob", jobRepository)
                .start(loadFactJobApplicationStep)
                .build();
    }
}