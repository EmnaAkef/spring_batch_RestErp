package com.spring_batch.RestErp.rh.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactJobOfferJobConfig {

    @Bean
    public Job loadFactJobOfferJob(
            JobRepository jobRepository,
            @Qualifier("loadFactJobOfferStep") Step loadFactJobOfferStep) {

        return new JobBuilder("loadFactJobOfferJob", jobRepository)
                .start(loadFactJobOfferStep)
                .build();
    }
}