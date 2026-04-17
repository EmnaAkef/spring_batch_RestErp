package com.spring_batch.RestErp.rh.batch.writer.config;

import com.spring_batch.RestErp.rh.batch.writer.itemWriter.DailyShiftTemplateItemWriter;
import com.spring_batch.RestErp.rh.dto.dim.DimDailyShiftTemplate;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DailyShiftTemplateWriterConfig {

    @Bean
    public ItemWriter<DimDailyShiftTemplate> dailyShiftTemplateWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {
        return new DailyShiftTemplateItemWriter(new JdbcTemplate(dwDataSource));
    }
}