package com.spring_batch.RestErp.sales.batch.writer.config;

import com.spring_batch.RestErp.sales.batch.writer.itemWriter.DealItemWriter;
import com.spring_batch.RestErp.sales.dto.fact.FactDeal;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DealWriterConfig {

    @Bean
    public ItemWriter<FactDeal> dealWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {
        return new DealItemWriter(new JdbcTemplate(dwDataSource));
    }
}