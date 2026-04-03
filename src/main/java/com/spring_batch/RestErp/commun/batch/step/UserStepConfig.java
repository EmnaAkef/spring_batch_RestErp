package com.spring_batch.RestErp.commun.batch.step;

import com.spring_batch.RestErp.commun.dto.source.UserSource;
import com.spring_batch.RestErp.commun.dto.dim.DimUser;
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
public class UserStepConfig {

    @Bean
    public Step loadDimUserStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<UserSource> userReader,
            ItemProcessor<UserSource, DimUser> userProcessor,
            ItemWriter<DimUser> userWriter) {

        return new StepBuilder("loadDimUserStep", jobRepository)
                .<UserSource, DimUser>chunk(10, transactionManager)
                .reader(userReader)
                .processor(userProcessor)
                .writer(userWriter)
                .build();
    }
}
