package com.spring_batch.RestErp.finance.batch.step;

import com.spring_batch.RestErp.finance.dto.fact.FactAsset;
import com.spring_batch.RestErp.finance.dto.source.FactAssetSource;
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
public class FactAssetStepConfig {

    @Bean
    public Step loadFactAssetStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<FactAssetSource> factAssetReader,
            ItemProcessor<FactAssetSource, FactAsset> factAssetProcessor,
            ItemWriter<FactAsset> factAssetWriter) {

        return new StepBuilder("loadFactAssetStep", jobRepository)
                .<FactAssetSource, FactAsset>chunk(1000, transactionManager)
                .reader(factAssetReader)
                .processor(factAssetProcessor)
                .writer(factAssetWriter)
                .build();
    }
}