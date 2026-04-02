package com.spring_batch.RestErp.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing(dataSourceRef = "dwDataSource", transactionManagerRef = "dwTransactionManager")
public class BatchConfig {

    @Configuration
    static class TransactionManagerConfig {

        @org.springframework.context.annotation.Bean(name = "dwTransactionManager")
        public PlatformTransactionManager dwTransactionManager(
                @Qualifier("dwDataSource") DataSource dwDataSource) {
            return new DataSourceTransactionManager(dwDataSource);
        }
    }
}