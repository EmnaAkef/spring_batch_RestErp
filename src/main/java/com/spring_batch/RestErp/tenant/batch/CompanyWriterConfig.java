package com.spring_batch.RestErp.tenant.batch;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.spring_batch.RestErp.tenant.dto.DimCompany;

@Configuration
public class CompanyWriterConfig {

    @Bean
    public ItemWriter<DimCompany> companyWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {
        return new CompanyItemWriter(new JdbcTemplate(dwDataSource));
    }
}