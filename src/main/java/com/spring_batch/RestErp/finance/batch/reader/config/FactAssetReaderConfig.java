package com.spring_batch.RestErp.finance.batch.reader.config;

import com.spring_batch.RestErp.finance.batch.reader.item.FactAssetItemReader;
import com.spring_batch.RestErp.finance.dto.source.FactAssetSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FactAssetReaderConfig {

    @Bean
    public ItemReader<FactAssetSource> factAssetReader(
            @Qualifier("erpDataSource") DataSource erpDataSource) {
        return new FactAssetItemReader(erpDataSource);
    }
}