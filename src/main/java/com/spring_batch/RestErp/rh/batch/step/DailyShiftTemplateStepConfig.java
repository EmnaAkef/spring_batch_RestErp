package com.spring_batch.RestErp.rh.batch.step;

import com.spring_batch.RestErp.rh.dto.dim.DimDailyShiftTemplate;
import com.spring_batch.RestErp.rh.dto.source.DailyShiftTemplateSource;
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
public class DailyShiftTemplateStepConfig {

    @Bean
    public Step loadDimDailyShiftTemplateStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<DailyShiftTemplateSource> dailyShiftTemplateReader,
            ItemProcessor<DailyShiftTemplateSource, DimDailyShiftTemplate> dailyShiftTemplateProcessor,
            ItemWriter<DimDailyShiftTemplate> dailyShiftTemplateWriter) {

        return new StepBuilder("loadDimDailyShiftTemplateStep", jobRepository)
                .<DailyShiftTemplateSource, DimDailyShiftTemplate>chunk(1000, transactionManager)
                .reader(dailyShiftTemplateReader)
                .processor(dailyShiftTemplateProcessor)
                .writer(dailyShiftTemplateWriter)
                .build();
    }
}