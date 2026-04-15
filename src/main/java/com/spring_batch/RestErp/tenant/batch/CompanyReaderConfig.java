package com.spring_batch.RestErp.tenant.batch;

import javax.sql.DataSource;

import com.spring_batch.RestErp.tenant.dto.CompanyRegistry;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompanyReaderConfig {

    @Bean
    public JdbcCursorItemReader<CompanyRegistry> tenantReader(
            @Qualifier("erpDataSource") DataSource erpDataSource) {

        JdbcCursorItemReader<CompanyRegistry> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(erpDataSource);
        reader.setSql("""
                select id, companyname, currency, city, country, archive, tenant_schema, employeecount, end_salary_month_day
                from public.company
                order by id
                """);

        reader.setRowMapper((rs, rowNum) -> {
            CompanyRegistry company = new CompanyRegistry();
            company.setCompanyId(rs.getLong("id"));
            company.setCompanyName(rs.getString("companyname"));
            company.setCity(rs.getString("city"));
            company.setCountry(rs.getString("country"));
            company.setCurrency(rs.getInt("currency"));
            company.setEmployeeCount(rs.getInt("employeecount"));
            company.setEndSalaryMonthDay(rs.getInt("end_salary_month_day"));
            company.setSchemaName(rs.getString("tenant_schema"));
            company.setActive(!rs.getBoolean("archive"));

            return company;
        });

        return reader;
    }
}