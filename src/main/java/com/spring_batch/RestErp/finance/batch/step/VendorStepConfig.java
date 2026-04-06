package com.spring_batch.RestErp.finance.batch.step;

import com.spring_batch.RestErp.finance.dto.dim.DimVendor;
import com.spring_batch.RestErp.finance.dto.source.VendorSource;
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
public class VendorStepConfig {

    @Bean
    public Step loadDimVendorStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<VendorSource> vendorReader,
            ItemProcessor<VendorSource, DimVendor> vendorProcessor,
            ItemWriter<DimVendor> vendorWriter) {

        return new StepBuilder("loadDimVendorStep", jobRepository)
                .<VendorSource, DimVendor>chunk(10, transactionManager)
                .reader(vendorReader)
                .processor(vendorProcessor)
                .writer(vendorWriter)
                .build();
    }
}
