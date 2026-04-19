package com.spring_batch.RestErp.rh.batch.writer.config;

import com.spring_batch.RestErp.rh.batch.writer.itemWriter.EmployeeHrItemWriter;
import com.spring_batch.RestErp.rh.dto.fact.FactEmployeeHr;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class EmployeeHrWriterConfig {

    @Bean
    public ItemWriter<FactEmployeeHr> employeeHrWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {
        return new EmployeeHrItemWriter(new JdbcTemplate(dwDataSource));
    }
}