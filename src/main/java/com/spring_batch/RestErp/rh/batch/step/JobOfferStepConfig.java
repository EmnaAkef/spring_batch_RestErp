package com.spring_batch.RestErp.rh.batch.step;

import com.spring_batch.RestErp.rh.dto.dim.DimJobOffer;
import com.spring_batch.RestErp.rh.dto.source.JobOfferSource;
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
public class JobOfferStepConfig {

    @Bean
    public Step loadDimJobOfferStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<JobOfferSource> jobOfferReader,
            ItemProcessor<JobOfferSource, DimJobOffer> jobOfferProcessor,
            ItemWriter<DimJobOffer> jobOfferWriter) {

        return new StepBuilder("loadDimJobOfferStep", jobRepository)
                .<JobOfferSource, DimJobOffer>chunk(10, transactionManager)
                .reader(jobOfferReader)
                .processor(jobOfferProcessor)
                .writer(jobOfferWriter)
                .build();
    }
}