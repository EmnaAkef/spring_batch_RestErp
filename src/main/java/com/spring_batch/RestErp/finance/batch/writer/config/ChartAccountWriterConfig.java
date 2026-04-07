package com.spring_batch.RestErp.finance.batch.writer.config;

import com.spring_batch.RestErp.finance.batch.writer.item.ChartAccountItemWriter;
import com.spring_batch.RestErp.finance.dto.dim.DimChartAccount;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class ChartAccountWriterConfig {

    @Bean
    public ItemWriter<DimChartAccount> chartAccountWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {
        return new ChartAccountItemWriter(new JdbcTemplate(dwDataSource));
    }
}
