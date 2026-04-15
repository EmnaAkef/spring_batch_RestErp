package com.spring_batch.RestErp.finance.batch.reader.config;

import com.spring_batch.RestErp.finance.batch.reader.item.FactBillItemReader;
import com.spring_batch.RestErp.finance.dto.source.FactBillSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FactBillReaderConfig {

    @Bean
    public ItemReader<FactBillSource> factBillReader(
            @Qualifier("erpDataSource") DataSource erpDataSource) {
        return new FactBillItemReader(erpDataSource);
    }
}