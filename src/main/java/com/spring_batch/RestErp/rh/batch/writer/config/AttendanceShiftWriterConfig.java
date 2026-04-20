package com.spring_batch.RestErp.rh.batch.writer.config;

import com.spring_batch.RestErp.rh.batch.writer.itemWriter.AttendanceShiftItemWriter;
import com.spring_batch.RestErp.rh.dto.fact.FactAttendanceShift;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class AttendanceShiftWriterConfig {

    @Bean
    public ItemWriter<FactAttendanceShift> attendanceShiftWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {
        return new AttendanceShiftItemWriter(new JdbcTemplate(dwDataSource));
    }
}