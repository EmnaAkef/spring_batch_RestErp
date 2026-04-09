package com.spring_batch.RestErp.finance.batch.writer.config;

import com.spring_batch.RestErp.finance.batch.writer.item.ChartBalanceSnapshotItemWriter;
import com.spring_batch.RestErp.finance.dto.source.ChartBalanceSnapshotSource;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class ChartBalanceSnapshotWriterConfig {

    @Bean
    public ItemWriter<ChartBalanceSnapshotSource> chartBalanceSnapshotWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {
        return new ChartBalanceSnapshotItemWriter(new JdbcTemplate(dwDataSource));
    }
}