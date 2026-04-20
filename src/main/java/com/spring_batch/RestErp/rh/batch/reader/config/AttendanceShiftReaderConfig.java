package com.spring_batch.RestErp.rh.batch.reader.config;

import com.spring_batch.RestErp.rh.batch.reader.itemReader.AttendanceShiftItemReader;
import com.spring_batch.RestErp.rh.dto.source.FactAttendanceShiftSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AttendanceShiftReaderConfig {

    @Bean
    public ItemReader<FactAttendanceShiftSource> attendanceShiftReader(
            @Qualifier("erpDataSource") DataSource erpDataSource) {
        return new AttendanceShiftItemReader(erpDataSource);
    }
}