package com.spring_batch.RestErp.finance.batch.reader.config;

import com.spring_batch.RestErp.finance.batch.reader.item.ChartAccountItemReader;
import com.spring_batch.RestErp.finance.dto.source.ChartAccountSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ChartAccountReaderConfig {

    @Bean
    public ItemReader<ChartAccountSource> chartAccountReader(
            @Qualifier("erpDataSource") DataSource erpDataSource) {
        return new ChartAccountItemReader(erpDataSource);
    }
}
