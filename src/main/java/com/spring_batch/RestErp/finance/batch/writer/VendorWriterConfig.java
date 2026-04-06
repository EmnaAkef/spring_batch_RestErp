package com.spring_batch.RestErp.finance.batch.writer;


import com.spring_batch.RestErp.finance.dto.dim.DimVendor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class VendorWriterConfig {

    @Bean
    public ItemWriter<DimVendor> vendorWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {
        return new VendorItemWriter(new JdbcTemplate(dwDataSource));
    }
}
