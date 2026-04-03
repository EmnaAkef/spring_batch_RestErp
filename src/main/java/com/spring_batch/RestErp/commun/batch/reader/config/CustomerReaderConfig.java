package com.spring_batch.RestErp.commun.batch.reader.config;

import com.spring_batch.RestErp.commun.batch.reader.itemReader.CustomerItemReader;
import com.spring_batch.RestErp.commun.dto.source.CustomerSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class CustomerReaderConfig {

    @Bean
    public ItemReader<CustomerSource> customerReader(
            @Qualifier("erpDataSource") DataSource erpDataSource) {
        return new CustomerItemReader(erpDataSource);
    }
}