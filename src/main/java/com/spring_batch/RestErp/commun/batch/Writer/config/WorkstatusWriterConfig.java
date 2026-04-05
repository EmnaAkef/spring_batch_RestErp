package com.spring_batch.RestErp.commun.batch.Writer.config;

import com.spring_batch.RestErp.commun.batch.Writer.itemWriter.WorkstatusItemWriter;
import com.spring_batch.RestErp.commun.dto.dim.DimWorkstatus;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class WorkstatusWriterConfig {

    @Bean
    public ItemWriter<DimWorkstatus> workstatusWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {
        return new WorkstatusItemWriter(new JdbcTemplate(dwDataSource));
    }
}