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
<<<<<<< HEAD
    private final Job loadDimJobOfferJob;
=======
    private final Job loadDimChartAccountJob;
>>>>>>> 14131b199347347b4ee743d29c051c2cb9f9f06c

    public JobStarter(
            JobLauncher jobLauncher,
            @Qualifier("loadDimCompanyJob") Job loadDimCompanyJob,
            @Qualifier("loadDimDepartmentJob") Job loadDimDepartmentJob,
            @Qualifier("loadDimUserJob") Job loadDimUserJob,
            @Qualifier("loadDimCustomerJob") Job loadDimCustomerJob,
            @Qualifier("loadDimWorkstatusJob") Job loadDimWorkstatusJob,
            @Qualifier("loadDimProductJob") Job loadDimProductJob,
            @Qualifier("loadDimVendorJob") Job loadDimVendorJob,
<<<<<<< HEAD
            @Qualifier("loadDimJobOfferJob") Job loadDimJobOfferJob) {
=======
            @Qualifier("loadDimChartAccountJob") Job loadDimChartAccountJob) {
>>>>>>> 14131b199347347b4ee743d29c051c2cb9f9f06c

        this.jobLauncher = jobLauncher;
        this.loadDimCompanyJob = loadDimCompanyJob;
        this.loadDimDepartmentJob = loadDimDepartmentJob;
        this.loadDimUserJob = loadDimUserJob;
        this.loadDimCustomerJob = loadDimCustomerJob;
        this.loadDimWorkstatusJob = loadDimWorkstatusJob;
        this.loadDimProductJob = loadDimProductJob;
        this.loadDimVendorJob = loadDimVendorJob;
<<<<<<< HEAD
        this.loadDimJobOfferJob = loadDimJobOfferJob;
=======
        this.loadDimChartAccountJob = loadDimChartAccountJob;
>>>>>>> 14131b199347347b4ee743d29c051c2cb9f9f06c
    }

    @Override
    public void run(String... args) throws Exception {

        long baseTime = System.currentTimeMillis();

        jobLauncher.run(
                loadDimCompanyJob,
                new JobParametersBuilder()
                        .addLong("time", baseTime)
                        .toJobParameters()
        );

        jobLauncher.run(
                loadDimDepartmentJob,
                new JobParametersBuilder()
                        .addLong("time", baseTime + 1)
                        .toJobParameters()
        );

        jobLauncher.run(
                loadDimUserJob,
                new JobParametersBuilder()
                        .addLong("time", baseTime + 2)
                        .toJobParameters()
        );

        jobLauncher.run(
                loadDimCustomerJob,
                new JobParametersBuilder()
                        .addLong("time", baseTime + 3)
                        .toJobParameters()
        );

        jobLauncher.run(
                loadDimWorkstatusJob,
                new JobParametersBuilder()
                        .addLong("time", baseTime + 4)
                        .toJobParameters()
        );

        jobLauncher.run(
                loadDimProductJob,
                new JobParametersBuilder()
                        .addLong("time", baseTime + 5)
                        .toJobParameters()
        );

        jobLauncher.run(
                loadDimVendorJob,
                new JobParametersBuilder()
                        .addLong("time", baseTime + 6)
                        .toJobParameters()
        );

        jobLauncher.run(
                loadDimJobOfferJob,
                new JobParametersBuilder()
                        .addLong("time", baseTime + 7)
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