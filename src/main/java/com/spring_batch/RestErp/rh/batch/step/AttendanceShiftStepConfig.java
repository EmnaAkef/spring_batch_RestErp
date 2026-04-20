package com.spring_batch.RestErp.rh.batch.step;

import com.spring_batch.RestErp.rh.dto.fact.FactAttendanceShift;
import com.spring_batch.RestErp.rh.dto.source.FactAttendanceShiftSource;
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
public class AttendanceShiftStepConfig {

    @Bean
    public Step loadFactAttendanceShiftStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<FactAttendanceShiftSource> attendanceShiftReader,
            ItemProcessor<FactAttendanceShiftSource, FactAttendanceShift> attendanceShiftProcessor,
            ItemWriter<FactAttendanceShift> attendanceShiftWriter) {

        return new StepBuilder("loadFactAttendanceShiftStep", jobRepository)
                .<FactAttendanceShiftSource, FactAttendanceShift>chunk(10, transactionManager)
                .reader(attendanceShiftReader)
                .processor(attendanceShiftProcessor)
                .writer(attendanceShiftWriter)
                .build();
    }
}