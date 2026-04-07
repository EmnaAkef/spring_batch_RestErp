package com.spring_batch.RestErp.rh.batch.reader;

import com.spring_batch.RestErp.rh.dto.source.JobOfferSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JobOfferReaderConfig {

    @Bean
    public ItemReader<JobOfferSource> jobOfferReader(
            @Qualifier("erpDataSource") DataSource erpDataSource) {
        return new JobOfferItemReader(erpDataSource);
    }
}