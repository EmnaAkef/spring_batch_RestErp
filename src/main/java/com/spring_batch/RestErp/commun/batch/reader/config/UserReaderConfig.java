package com.spring_batch.RestErp.commun.batch.reader.config;

import com.spring_batch.RestErp.commun.batch.reader.itemReader.UserItemReader;
import com.spring_batch.RestErp.commun.dto.source.UserSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class UserReaderConfig {

    @Bean
    public ItemReader<UserSource> userReader(
            @Qualifier("erpDataSource") DataSource dataSource) {
        return new UserItemReader(dataSource);
    }
}
