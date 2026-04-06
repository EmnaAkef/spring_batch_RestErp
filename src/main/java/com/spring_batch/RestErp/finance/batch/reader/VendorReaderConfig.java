package com.spring_batch.RestErp.finance.batch.reader;

import com.spring_batch.RestErp.finance.dto.source.VendorSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class VendorReaderConfig {

    @Bean
    public ItemReader<VendorSource> vendorReader(
            @Qualifier("erpDataSource") DataSource erpDataSource) {
        return new VendorItemReader(erpDataSource);
    }
}