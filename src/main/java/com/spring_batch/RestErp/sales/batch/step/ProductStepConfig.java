package com.spring_batch.RestErp.sales.batch.step;


import com.spring_batch.RestErp.sales.dto.dim.DimProduct;
import com.spring_batch.RestErp.sales.dto.source.ProductSource;
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
public class ProductStepConfig {

    @Bean
    public Step loadDimProductStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<ProductSource> productReader,
            ItemProcessor<ProductSource, DimProduct> productProcessor,
            ItemWriter<DimProduct> productWriter) {

        return new StepBuilder("loadDimProductStep", jobRepository)
                .<ProductSource, DimProduct>chunk(10, transactionManager)
                .reader(productReader)
                .processor(productProcessor)
                .writer(productWriter)
                .build();
    }
}