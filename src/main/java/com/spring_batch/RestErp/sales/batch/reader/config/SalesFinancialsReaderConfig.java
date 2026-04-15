package com.spring_batch.RestErp.sales.batch.reader.config;

import com.spring_batch.RestErp.sales.batch.reader.itemReader.SalesFinancialsItemReader;
import com.spring_batch.RestErp.sales.dto.source.FactSalesFinancialsSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SalesFinancialsReaderConfig {

    @Bean
    public ItemReader<FactSalesFinancialsSource> salesFinancialsReader(
            @Qualifier("erpDataSource") DataSource erpDataSource) {
        return new SalesFinancialsItemReader(erpDataSource);
    }
}