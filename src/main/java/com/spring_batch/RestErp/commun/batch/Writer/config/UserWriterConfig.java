package com.spring_batch.RestErp.commun.batch.Writer.config;

import com.spring_batch.RestErp.commun.batch.Writer.itemWriter.UserItemWriter;
import com.spring_batch.RestErp.commun.dto.dim.DimUser;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class UserWriterConfig {

    @Bean
    public ItemWriter<DimUser> userWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {

        return new UserItemWriter(new JdbcTemplate(dwDataSource));
    }
}
