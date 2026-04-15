package com.spring_batch.RestErp.sales.batch.reader.config;

import com.spring_batch.RestErp.sales.batch.reader.itemReader.DealItemReader;
import com.spring_batch.RestErp.sales.dto.source.FactDealSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DealReaderConfig {

    @Bean
    public ItemReader<FactDealSource> dealReader(
            @Qualifier("erpDataSource") DataSource erpDataSource) {
        return new DealItemReader(erpDataSource);
    }
}