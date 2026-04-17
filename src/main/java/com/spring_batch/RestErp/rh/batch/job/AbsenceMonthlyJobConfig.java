package com.spring_batch.RestErp.rh.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AbsenceMonthlyJobConfig {

    @Bean
    public Job loadFactAbsenceMonthlyJob(
            JobRepository jobRepository,
            Step loadFactAbsenceMonthlyStep) {

        return new JobBuilder("loadFactAbsenceMonthlyJob", jobRepository)
                .start(loadFactAbsenceMonthlyStep)
                .build();
    }
}