package com.spring_batch.RestErp.rh.batch.reader.config;

import com.spring_batch.RestErp.rh.batch.reader.item.FactJobApplicationItemReader;
import com.spring_batch.RestErp.rh.dto.source.FactJobApplicationSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FactJobApplicationReaderConfig {

    @Bean
    public ItemReader<FactJobApplicationSource> factJobApplicationReader(
            @Qualifier("erpDataSource") DataSource erpDataSource) {
        return new FactJobApplicationItemReader(erpDataSource);
    }
}