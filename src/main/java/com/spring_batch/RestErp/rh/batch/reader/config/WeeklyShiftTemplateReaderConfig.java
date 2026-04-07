package com.spring_batch.RestErp.rh.batch.reader.config;

import com.spring_batch.RestErp.rh.batch.reader.item.WeeklyShiftTemplateItemReader;
import com.spring_batch.RestErp.rh.dto.source.WeeklyShiftTemplateSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class WeeklyShiftTemplateReaderConfig {

    @Bean
    public ItemReader<WeeklyShiftTemplateSource> weeklyShiftTemplateReader(
            @Qualifier("erpDataSource") DataSource erpDataSource) {
        return new WeeklyShiftTemplateItemReader(erpDataSource);
    }
}