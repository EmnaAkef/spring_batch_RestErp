package com.spring_batch.RestErp.commun.batch.Writer.config;

import com.spring_batch.RestErp.commun.batch.Writer.itemWriter.CustomerItemWriter;
import com.spring_batch.RestErp.commun.dto.dim.DimCustomer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class CustomerWriterConfig {

    @Bean
    public ItemWriter<DimCustomer> customerWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {
        return new CustomerItemWriter(new JdbcTemplate(dwDataSource));
    }
}