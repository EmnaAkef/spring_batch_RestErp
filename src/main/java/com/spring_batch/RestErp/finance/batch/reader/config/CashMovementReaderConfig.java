package com.spring_batch.RestErp.finance.batch.reader.config;

import com.spring_batch.RestErp.finance.batch.reader.item.CashMovementItemReader;
import com.spring_batch.RestErp.finance.dto.source.CashMovementSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class CashMovementReaderConfig {

    @Bean
    public ItemReader<CashMovementSource> cashMovementReader(
            @Qualifier("erpDataSource") DataSource erpDataSource) {
        return new CashMovementItemReader(erpDataSource);
    }
}