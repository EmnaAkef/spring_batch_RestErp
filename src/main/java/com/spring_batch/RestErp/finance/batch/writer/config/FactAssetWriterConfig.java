package com.spring_batch.RestErp.finance.batch.writer.config;

import com.spring_batch.RestErp.finance.batch.writer.item.FactAssetItemWriter;
import com.spring_batch.RestErp.finance.dto.fact.FactAsset;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class FactAssetWriterConfig {

    @Bean
    public ItemWriter<FactAsset> factAssetWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {
        return new FactAssetItemWriter(new JdbcTemplate(dwDataSource));
    }
}