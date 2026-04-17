package com.spring_batch.RestErp.rh.batch.writer.config;

import com.spring_batch.RestErp.rh.batch.writer.itemWriter.JobOfferItemWriter;
import com.spring_batch.RestErp.rh.dto.dim.DimJobOffer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JobOfferWriterConfig {

    @Bean
    public ItemWriter<DimJobOffer> jobOfferWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {
        return new JobOfferItemWriter(new JdbcTemplate(dwDataSource));
    }
}