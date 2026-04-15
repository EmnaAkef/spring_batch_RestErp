package com.spring_batch.RestErp.finance.batch.writer.config;

import com.spring_batch.RestErp.finance.batch.writer.item.FactBillItemWriter;
import com.spring_batch.RestErp.finance.dto.fact.FactBill;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class FactBillWriterConfig {

    @Bean
    public ItemWriter<FactBill> factBillWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {
        return new FactBillItemWriter(new JdbcTemplate(dwDataSource));
    }
}