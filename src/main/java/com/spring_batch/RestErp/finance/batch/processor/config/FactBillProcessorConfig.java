package com.spring_batch.RestErp.finance.batch.processor.config;

import com.spring_batch.RestErp.finance.batch.processor.FactBillProcessor;
import com.spring_batch.RestErp.finance.dto.fact.FactBill;
import com.spring_batch.RestErp.finance.dto.source.FactBillSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactBillProcessorConfig {

    @Bean
    public ItemProcessor<FactBillSource, FactBill> factBillProcessor() {
        return new FactBillProcessor();
    }
}