package com.spring_batch.RestErp.commun.batch.reader.config;

import com.spring_batch.RestErp.commun.batch.reader.itemReader.WorkstatusItemReader;
import com.spring_batch.RestErp.commun.dto.source.WorkstatusSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class WorkstatusReaderConfig {

    @Bean
    public ItemReader<WorkstatusSource> workstatusReader(
            @Qualifier("erpDataSource") DataSource erpDataSource) {
        return new WorkstatusItemReader(erpDataSource);
    }
}