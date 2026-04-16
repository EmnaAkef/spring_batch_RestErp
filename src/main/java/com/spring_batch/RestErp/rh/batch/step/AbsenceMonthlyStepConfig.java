package com.spring_batch.RestErp.rh.batch.step;

import com.spring_batch.RestErp.rh.dto.fact.FactAbsenceMonthly;
import com.spring_batch.RestErp.rh.dto.source.FactAbsenceMonthlySource;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AbsenceMonthlyStepConfig {

    @Bean
    public Step loadFactAbsenceMonthlyStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<FactAbsenceMonthlySource> absenceMonthlyReader,
            ItemProcessor<FactAbsenceMonthlySource, FactAbsenceMonthly> absenceMonthlyProcessor,
            ItemWriter<FactAbsenceMonthly> absenceMonthlyWriter) {

        return new StepBuilder("loadFactAbsenceMonthlyStep", jobRepository)
                .<FactAbsenceMonthlySource, FactAbsenceMonthly>chunk(10, transactionManager)
                .reader(absenceMonthlyReader)
                .processor(absenceMonthlyProcessor)
                .writer(absenceMonthlyWriter)
                .build();
    }
}