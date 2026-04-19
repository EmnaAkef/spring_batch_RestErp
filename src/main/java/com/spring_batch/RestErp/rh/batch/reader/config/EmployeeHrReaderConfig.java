package com.spring_batch.RestErp.rh.batch.reader.config;

import com.spring_batch.RestErp.rh.batch.reader.itemReader.EmployeeHrItemReader;
import com.spring_batch.RestErp.rh.dto.source.FactEmployeeHrSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class EmployeeHrReaderConfig {

    @Bean
    public ItemReader<FactEmployeeHrSource> employeeHrReader(
            @Qualifier("erpDataSource") DataSource erpDataSource) {
        return new EmployeeHrItemReader(erpDataSource);
    }
}