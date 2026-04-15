package com.spring_batch.RestErp.finance.batch.reader.config;

import com.spring_batch.RestErp.finance.batch.reader.item.FactInvoiceItemReader;
import com.spring_batch.RestErp.finance.dto.source.FactInvoiceSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FactInvoiceReaderConfig {

    @Bean
    public ItemReader<FactInvoiceSource> factInvoiceReader(
            @Qualifier("erpDataSource") DataSource erpDataSource) {
        return new FactInvoiceItemReader(erpDataSource);
    }
}