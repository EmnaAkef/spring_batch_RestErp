package com.spring_batch.RestErp.finance.batch.processor.config;

import com.spring_batch.RestErp.finance.batch.processor.CashMovementProcessor;
import com.spring_batch.RestErp.finance.dto.fact.FactCashMovement;
import com.spring_batch.RestErp.finance.dto.source.CashMovementSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CashMovementProcessorConfig {

    @Bean
    public ItemProcessor<CashMovementSource, FactCashMovement> cashMovementProcessor() {
        return new CashMovementProcessor();
    }
}