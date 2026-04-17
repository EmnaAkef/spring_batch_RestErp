package com.spring_batch.RestErp.rh.batch.step;

import com.spring_batch.RestErp.rh.dto.fact.FactJobOffer;
import com.spring_batch.RestErp.rh.dto.source.FactJobOfferSource;
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
public class FactJobOfferStepConfig {

    @Bean
    public Step loadFactJobOfferStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<FactJobOfferSource> factJobOfferReader,
            ItemProcessor<FactJobOfferSource, FactJobOffer> factJobOfferProcessor,
            ItemWriter<FactJobOffer> factJobOfferWriter) {

        return new StepBuilder("loadFactJobOfferStep", jobRepository)
                .<FactJobOfferSource, FactJobOffer>chunk(1000, transactionManager)
                .reader(factJobOfferReader)
                .processor(factJobOfferProcessor)
                .writer(factJobOfferWriter)
                .build();
    }
}