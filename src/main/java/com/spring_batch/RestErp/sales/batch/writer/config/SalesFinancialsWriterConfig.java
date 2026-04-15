package com.spring_batch.RestErp.sales.batch.writer.config;

import com.spring_batch.RestErp.sales.batch.writer.itemWriter.SalesFinancialsItemWriter;
import com.spring_batch.RestErp.sales.dto.fact.FactSalesFinancials;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class SalesFinancialsWriterConfig {

    @Bean
    public ItemWriter<FactSalesFinancials> salesFinancialsWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {
        return new SalesFinancialsItemWriter(new JdbcTemplate(dwDataSource));
    }
}