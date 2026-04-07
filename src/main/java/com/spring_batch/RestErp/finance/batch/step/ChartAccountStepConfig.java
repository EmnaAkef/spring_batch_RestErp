package com.spring_batch.RestErp.finance.batch.step;

import com.spring_batch.RestErp.finance.dto.dim.DimChartAccount;
import com.spring_batch.RestErp.finance.dto.source.ChartAccountSource;
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
public class ChartAccountStepConfig {

    @Bean
    public Step loadDimChartAccountStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<ChartAccountSource> chartAccountReader,
            ItemProcessor<ChartAccountSource, DimChartAccount> chartAccountProcessor,
            ItemWriter<DimChartAccount> chartAccountWriter) {

        return new StepBuilder("loadDimChartAccountStep", jobRepository)
                .<ChartAccountSource, DimChartAccount>chunk(10, transactionManager)
                .reader(chartAccountReader)
                .processor(chartAccountProcessor)
                .writer(chartAccountWriter)
                .build();
    }
}
