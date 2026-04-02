package com.emna.spring_batch_test.commun.batch.processor;

import java.time.LocalDateTime;

import javax.sql.DataSource;

import com.emna.spring_batch_test.commun.dto.dim.DimEmployee;
import com.emna.spring_batch_test.commun.dto.source.SourceEmployee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class EmployeeProcessorConfig {

    @Bean
    public ItemProcessor<SourceEmployee, DimEmployee> employeeProcessor(
            @Qualifier("dwDataSource") DataSource dwDataSource) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dwDataSource);

        return source -> {
            Long tenantKey = jdbcTemplate.queryForObject("""
                    select tenant_key
                    from dim_tenant
                    where tenant_id_source = ?
                    """, Long.class, source.getTenantIdSource());

            DimEmployee target = new DimEmployee();
            target.setTenantKey(tenantKey);
            target.setEmployeeIdSource(source.getEmployeeId());
            target.setEmployeeCode(source.getEmployeeCode());
            target.setFullName(source.getFirstName() + " " + source.getLastName());
            target.setDepartmentName(source.getDepartmentName());
            target.setHireDate(source.getHireDate());
            target.setActive(source.getActive());
            target.setEffectiveFrom(LocalDateTime.now());

            return target;
        };
    }
}