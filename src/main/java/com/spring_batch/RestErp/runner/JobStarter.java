package com.spring_batch.RestErp.runner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobStarter implements CommandLineRunner {

    private final JobLauncher jobLauncher;

    private final Job loadDimCompanyJob;
    private final Job loadDimDepartmentJob;
    private final Job loadDimUserJob;
    private final Job loadDimCustomerJob;

    public JobStarter(
            JobLauncher jobLauncher,
            @Qualifier("loadDimCompanyJob") Job loadDimCompanyJob,
            @Qualifier("loadDimDepartmentJob") Job loadDimDepartmentJob,
            @Qualifier("loadDimUserJob") Job loadDimUserJob,
            @Qualifier("loadDimCustomerJob") Job loadDimCustomerJob) {

        this.jobLauncher = jobLauncher;
        this.loadDimCompanyJob = loadDimCompanyJob;
        this.loadDimDepartmentJob = loadDimDepartmentJob;
        this.loadDimUserJob = loadDimUserJob;
        this.loadDimCustomerJob = loadDimCustomerJob;
    }

    @Override
    public void run(String... args) throws Exception {

        long baseTime = System.currentTimeMillis();

        // 1) Charger d'abord company
        jobLauncher.run(
                loadDimCompanyJob,
                new JobParametersBuilder()
                        .addLong("time", baseTime)
                        .toJobParameters()
        );

        // 2) Ensuite department
        jobLauncher.run(
                loadDimDepartmentJob,
                new JobParametersBuilder()
                        .addLong("time", baseTime + 1)
                        .toJobParameters()
        );

        // 3) Ensuite user
        jobLauncher.run(
                loadDimUserJob,
                new JobParametersBuilder()
                        .addLong("time", baseTime + 2)
                        .toJobParameters()
        );

        // 4) Ensuite customer
        jobLauncher.run(
                loadDimCustomerJob,
                new JobParametersBuilder()
                        .addLong("time", baseTime + 3)
                        .toJobParameters()
        );
    }
}