package com.emna.spring_batch_test.commun.batch.Writer;

import javax.sql.DataSource;

import com.emna.spring_batch_test.commun.dto.dim.DimEmployee;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeWriterConfig {

    @Bean
    public JdbcBatchItemWriter<DimEmployee> employeeWriter(
            @Qualifier("dwDataSource") DataSource dwDataSource) {

        return new JdbcBatchItemWriterBuilder<DimEmployee>()
                .dataSource(dwDataSource)
                .sql("""
                        insert into dim_employee
                        (tenant_key, employee_id_source, employee_code, full_name,
                         department_name, hire_date, active, effective_from)
                        values
                        (:tenantKey, :employeeIdSource, :employeeCode, :fullName,
                         :departmentName, :hireDate, :active, :effectiveFrom)
                        on conflict (tenant_key, employee_id_source)
                        do update set
                            employee_code = excluded.employee_code,
                            full_name = excluded.full_name,
                            department_name = excluded.department_name,
                            hire_date = excluded.hire_date,
                            active = excluded.active
                        """)
                .beanMapped()
                .build();
    }
}