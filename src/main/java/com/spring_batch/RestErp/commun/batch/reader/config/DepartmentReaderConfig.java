package com.spring_batch.RestErp.commun.batch.reader.config;

import javax.sql.DataSource;

import com.spring_batch.RestErp.commun.batch.reader.itemReader.DepartmentItemReader;
import com.spring_batch.RestErp.commun.dto.source.DepartmentSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DepartmentReaderConfig {

    @Bean
    public ItemReader<DepartmentSource> departmentReader(
            @Qualifier("erpDataSource") DataSource erpDataSource) {
        return new DepartmentItemReader(erpDataSource);
    }
}
