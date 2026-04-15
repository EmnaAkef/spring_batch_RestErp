package com.spring_batch.RestErp.finance.batch.step;

import com.spring_batch.RestErp.finance.dto.fact.FactInvoice;
import com.spring_batch.RestErp.finance.dto.source.FactInvoiceSource;
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
public class FactInvoiceStepConfig {

    @Bean
    public Step loadFactInvoiceStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<FactInvoiceSource> factInvoiceReader,
            ItemProcessor<FactInvoiceSource, FactInvoice> factInvoiceProcessor,
            ItemWriter<FactInvoice> factInvoiceItemWriter) {

        return new StepBuilder("loadFactInvoiceStep", jobRepository)
                .<FactInvoiceSource, FactInvoice>chunk(1000, transactionManager)
                .reader(factInvoiceReader)
                .processor(factInvoiceProcessor)
                .writer(factInvoiceItemWriter)
                .build();
    }
}