package com.spring_batch.RestErp.rh.batch.step;

import com.spring_batch.RestErp.rh.dto.dim.DimWeeklyShiftTemplate;
import com.spring_batch.RestErp.rh.dto.source.WeeklyShiftTemplateSource;
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
public class WeeklyShiftTemplateStepConfig {

    @Bean
    public Step loadDimWeeklyShiftTemplateStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<WeeklyShiftTemplateSource> weeklyShiftTemplateReader,
            ItemProcessor<WeeklyShiftTemplateSource, DimWeeklyShiftTemplate> weeklyShiftTemplateProcessor,
            ItemWriter<DimWeeklyShiftTemplate> weeklyShiftTemplateWriter) {

        return new StepBuilder("loadDimWeeklyShiftTemplateStep", jobRepository)
                .<WeeklyShiftTemplateSource, DimWeeklyShiftTemplate>chunk(10, transactionManager)
                .reader(weeklyShiftTemplateReader)
                .processor(weeklyShiftTemplateProcessor)
                .writer(weeklyShiftTemplateWriter)
                .build();
    }
}