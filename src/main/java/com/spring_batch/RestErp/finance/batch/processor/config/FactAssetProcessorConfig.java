package com.spring_batch.RestErp.finance.batch.processor.config;

import com.spring_batch.RestErp.finance.batch.processor.FactAssetProcessor;
import com.spring_batch.RestErp.finance.dto.fact.FactAsset;
import com.spring_batch.RestErp.finance.dto.source.FactAssetSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactAssetProcessorConfig {

    @Bean
    public ItemProcessor<FactAssetSource, FactAsset> factAssetProcessor() {
        return new FactAssetProcessor();
    }
}