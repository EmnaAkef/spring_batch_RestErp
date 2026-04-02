package com.spring_batch.RestErp.commun.batch.step;


import com.spring_batch.RestErp.commun.dto.dim.DimDepartment;
import com.spring_batch.RestErp.commun.dto.source.DepartmentSource;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DepartmentStepConfig {

    @Bean
    public Step loadDimDepartmentStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<DepartmentSource> departmentReader,
            ItemProcessor<DepartmentSource, DimDepartment> departmentProcessor,
            ItemWriter<DimDepartment> departmentWriter) {

        return new StepBuilder("loadDimDepartmentStep", jobRepository)
                .<DepartmentSource, DimDepartment>chunk(10, transactionManager)
                .reader(departmentReader)
                .processor(departmentProcessor)
                .writer(departmentWriter)
                .build();
    }
}
