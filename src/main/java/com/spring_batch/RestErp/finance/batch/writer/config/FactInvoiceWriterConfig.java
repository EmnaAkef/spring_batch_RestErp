package com.spring_batch.RestErp.finance.batch.writer.config;

import com.spring_batch.RestErp.finance.batch.writer.item.FactInvoiceItemWriter;
import com.spring_batch.RestErp.finance.dto.fact.FactInvoice;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class FactInvoiceWriterConfig {

    @Bean
    public ItemWriter<FactInvoice> factInvoiceWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {
        return new FactInvoiceItemWriter(new JdbcTemplate(dwDataSource));
    }
}