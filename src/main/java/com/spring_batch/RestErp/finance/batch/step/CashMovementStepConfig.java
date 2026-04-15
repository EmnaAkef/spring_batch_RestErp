package com.spring_batch.RestErp.finance.batch.step;

import com.spring_batch.RestErp.finance.dto.fact.FactCashMovement;
import com.spring_batch.RestErp.finance.dto.source.CashMovementSource;
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
public class CashMovementStepConfig {

    @Bean
    public Step loadFactCashMovementStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<CashMovementSource> cashMovementReader,
            ItemProcessor<CashMovementSource, FactCashMovement> cashMovementProcessor,
            ItemWriter<FactCashMovement> cashMovementWriter) {

        return new StepBuilder("loadFactCashMovementStep", jobRepository)
                .<CashMovementSource, FactCashMovement>chunk(1000, transactionManager)
                .reader(cashMovementReader)
                .processor(cashMovementProcessor)
                .writer(cashMovementWriter)
                .build();
    }
}