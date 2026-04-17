package com.spring_batch.RestErp.rh.batch.writer.config;

import com.spring_batch.RestErp.rh.batch.writer.itemWriter.AbsenceMonthlyItemWriter;
import com.spring_batch.RestErp.rh.dto.fact.FactAbsenceMonthly;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class AbsenceMonthlyWriterConfig {

    @Bean
    public ItemWriter<FactAbsenceMonthly> absenceMonthlyWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {
        return new AbsenceMonthlyItemWriter(new JdbcTemplate(dwDataSource));
    }
}