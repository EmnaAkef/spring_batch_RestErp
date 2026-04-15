package com.spring_batch.RestErp.sales.batch.writer.config;

import com.spring_batch.RestErp.sales.batch.writer.itemWriter.SalesOrderItemWriter;
import com.spring_batch.RestErp.sales.dto.fact.FactSalesOrder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class SalesOrderWriterConfig {

    @Bean
    public ItemWriter<FactSalesOrder> salesOrderWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {
        return new SalesOrderItemWriter(new JdbcTemplate(dwDataSource));
    }
}