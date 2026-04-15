package com.spring_batch.RestErp.finance.batch.processor.config;

import com.spring_batch.RestErp.finance.batch.processor.FactInvoiceProcessor;
import com.spring_batch.RestErp.finance.dto.fact.FactInvoice;
import com.spring_batch.RestErp.finance.dto.source.FactInvoiceSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactInvoiceProcessorConfig {

    @Bean
    public ItemProcessor<FactInvoiceSource, FactInvoice> factInvoiceProcessor() {
        return new FactInvoiceProcessor();
    }
}