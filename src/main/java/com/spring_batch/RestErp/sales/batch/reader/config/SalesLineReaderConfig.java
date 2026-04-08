package com.spring_batch.RestErp.sales.batch.reader.config;

import com.spring_batch.RestErp.sales.batch.reader.itemReader.SalesLineItemReader;
import com.spring_batch.RestErp.sales.dto.source.FactSalesLineSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SalesLineReaderConfig {

    @Bean
    public ItemReader<FactSalesLineSource> salesLineReader(
            @Qualifier("erpDataSource") DataSource erpDataSource) {
        return new SalesLineItemReader(erpDataSource);
    }
}