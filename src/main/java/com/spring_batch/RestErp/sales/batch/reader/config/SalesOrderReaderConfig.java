package com.spring_batch.RestErp.sales.batch.reader.config;

import com.spring_batch.RestErp.sales.batch.reader.itemReader.SalesOrderItemReader;
import com.spring_batch.RestErp.sales.dto.source.FactSalesOrderSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SalesOrderReaderConfig {

    @Bean
    public ItemReader<FactSalesOrderSource> salesOrderReader(
            @Qualifier("erpDataSource") DataSource erpDataSource) {
        return new SalesOrderItemReader(erpDataSource);
    }
}