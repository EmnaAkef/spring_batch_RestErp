package com.spring_batch.RestErp.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {


    @Bean
    @ConfigurationProperties("app.datasource.erp")
    public DataSourceProperties erpDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "erpDataSource")
    public DataSource erpDataSource() {
        return erpDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @ConfigurationProperties("app.datasource.dw")
    public DataSourceProperties dwDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "dwDataSource")
    @Primary
    @BatchDataSource
    public DataSource dwDataSource() {
        return dwDataSourceProperties().initializeDataSourceBuilder().build();
    }
}