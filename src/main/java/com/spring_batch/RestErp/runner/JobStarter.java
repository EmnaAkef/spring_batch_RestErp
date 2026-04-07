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
    private final Job loadDimWorkstatusJob;
    private final Job loadDimProductJob;
    private final Job loadDimVendorJob;
    private final Job loadDimChartAccountJob;

    public JobStarter(
            JobLauncher jobLauncher,
            @Qualifier("loadDimCompanyJob") Job loadDimCompanyJob,
            @Qualifier("loadDimDepartmentJob") Job loadDimDepartmentJob,
            @Qualifier("loadDimUserJob") Job loadDimUserJob,
            @Qualifier("loadDimCustomerJob") Job loadDimCustomerJob,
            @Qualifier("loadDimWorkstatusJob") Job loadDimWorkstatusJob,
            @Qualifier("loadDimProductJob") Job loadDimProductJob,
            @Qualifier("loadDimVendorJob") Job loadDimVendorJob,
            @Qualifier("loadDimChartAccountJob") Job loadDimChartAccountJob) {

        this.jobLauncher = jobLauncher;
        this.loadDimCompanyJob = loadDimCompanyJob;
        this.loadDimDepartmentJob = loadDimDepartmentJob;
        this.loadDimUserJob = loadDimUserJob;
        this.loadDimCustomerJob = loadDimCustomerJob;
        this.loadDimWorkstatusJob = loadDimWorkstatusJob;
        this.loadDimProductJob = loadDimProductJob;
        this.loadDimVendorJob = loadDimVendorJob;
        this.loadDimChartAccountJob = loadDimChartAccountJob;
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

        // 5) Ensuite workstatus
        jobLauncher.run(
                loadDimWorkstatusJob,
                new JobParametersBuilder()
                        .addLong("time", baseTime + 4)
                        .toJobParameters()
        );
        // 6) Ensuite product
        jobLauncher.run(
                loadDimProductJob,
                new JobParametersBuilder()
                        .addLong("time", baseTime + 5)
                        .toJobParameters()
        );

        jobLauncher.run(
                loadDimVendorJob,
                new JobParametersBuilder()
                        .addLong("time", baseTime + 5)
                        .toJobParameters()
        );

        jobLauncher.run(
                loadDimChartAccountJob,
                new JobParametersBuilder()
                        .addLong("time", baseTime + 5)
                        .toJobParameters()
        );
    }
}