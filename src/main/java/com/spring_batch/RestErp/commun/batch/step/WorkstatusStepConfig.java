package com.spring_batch.RestErp.commun.batch.step;

import com.spring_batch.RestErp.commun.dto.dim.DimWorkstatus;
import com.spring_batch.RestErp.commun.dto.source.WorkstatusSource;
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
public class WorkstatusStepConfig {

    @Bean
    public Step loadDimWorkstatusStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<WorkstatusSource> workstatusReader,
            ItemProcessor<WorkstatusSource, DimWorkstatus> workstatusProcessor,
            ItemWriter<DimWorkstatus> workstatusWriter) {

        return new StepBuilder("loadDimWorkstatusStep", jobRepository)
                .<WorkstatusSource, DimWorkstatus>chunk(10, transactionManager)
                .reader(workstatusReader)
                .processor(workstatusProcessor)
                .writer(workstatusWriter)
                .build();
    }
}