package com.spring_batch.RestErp.rh.batch.writer.config;

import com.spring_batch.RestErp.rh.batch.writer.item.FactJobApplicationItemWriter;
import com.spring_batch.RestErp.rh.dto.fact.FactJobApplication;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class FactJobApplicationWriterConfig {

    @Bean
    public ItemWriter<FactJobApplication> factJobApplicationWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {
        return new FactJobApplicationItemWriter(new JdbcTemplate(dwDataSource));
    }
}