package com.spring_batch.RestErp.rh.batch.processor;

import com.spring_batch.RestErp.rh.dto.dim.DimJobOffer;
import com.spring_batch.RestErp.rh.dto.source.JobOfferSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobOfferProcessorConfig {

    @Bean
    public ItemProcessor<JobOfferSource, DimJobOffer> jobOfferProcessor() {
        return new JobOfferItemProcessor();
    }
}