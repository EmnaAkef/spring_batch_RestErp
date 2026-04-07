package com.spring_batch.RestErp.rh.batch.writer.config;

import com.spring_batch.RestErp.rh.batch.writer.item.WeeklyShiftTemplateItemWriter;
import com.spring_batch.RestErp.rh.dto.dim.DimWeeklyShiftTemplate;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class WeeklyShiftTemplateWriterConfig {

    @Bean
    public ItemWriter<DimWeeklyShiftTemplate> weeklyShiftTemplateWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {
        return new WeeklyShiftTemplateItemWriter(new JdbcTemplate(dwDataSource));
    }
}