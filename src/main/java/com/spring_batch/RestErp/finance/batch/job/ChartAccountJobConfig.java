package com.spring_batch.RestErp.finance.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChartAccountJobConfig {

    @Bean
    public Job loadDimChartAccountJob(
            JobRepository jobRepository,
            Step loadDimChartAccountStep) {

        return new JobBuilder("loadDimChartAccountJob", jobRepository)
                .start(loadDimChartAccountStep)
                .build();
    }
}
