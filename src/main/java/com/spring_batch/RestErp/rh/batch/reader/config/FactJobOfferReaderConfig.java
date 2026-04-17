package com.spring_batch.RestErp.rh.batch.reader.config;

import com.spring_batch.RestErp.rh.batch.reader.itemReader.FactJobOfferItemReader;
import com.spring_batch.RestErp.rh.dto.source.FactJobOfferSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FactJobOfferReaderConfig {

    @Bean
    public ItemReader<FactJobOfferSource> factJobOfferReader(
            @Qualifier("erpDataSource") DataSource erpDataSource) {
        return new FactJobOfferItemReader(erpDataSource);
    }
}