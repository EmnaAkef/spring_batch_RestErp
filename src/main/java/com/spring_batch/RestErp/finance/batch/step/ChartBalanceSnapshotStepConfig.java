package com.spring_batch.RestErp.finance.batch.step;

import com.spring_batch.RestErp.finance.dto.source.ChartBalanceSnapshotSource;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ChartBalanceSnapshotStepConfig {

    @Bean
    public Step loadFactChartBalanceSnapshotStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<ChartBalanceSnapshotSource> chartBalanceSnapshotReader,
            ItemWriter<ChartBalanceSnapshotSource> chartBalanceSnapshotWriter) {

        return new StepBuilder("loadFactChartBalanceSnapshotStep", jobRepository)
                .<ChartBalanceSnapshotSource, ChartBalanceSnapshotSource>chunk(1000, transactionManager)
                .reader(chartBalanceSnapshotReader)
                .writer(chartBalanceSnapshotWriter)
                .build();
    }
}