package com.spring_batch.RestErp.sales.batch.writer.config;



import com.spring_batch.RestErp.sales.batch.writer.itemWriter.ProductItemWriter;
import com.spring_batch.RestErp.sales.dto.dim.DimProduct;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class ProductWriterConfig {

    @Bean
    public ItemWriter<DimProduct> productWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {
        return new ProductItemWriter(new JdbcTemplate(dwDataSource));
    }
}