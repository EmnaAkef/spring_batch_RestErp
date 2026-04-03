package com.spring_batch.RestErp.runner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
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

    public JobStarter(
            JobLauncher jobLauncher,
            @Qualifier("loadDimCompanyJob") Job loadDimCompanyJob,
            @Qualifier("loadDimDepartmentJob") Job loadDimDepartmentJob,
            @Qualifier("loadDimUserJob") Job loadDimUserJob) {

        this.jobLauncher = jobLauncher;
        this.loadDimCompanyJob = loadDimCompanyJob;
        this.loadDimDepartmentJob = loadDimDepartmentJob;
        this.loadDimUserJob = loadDimUserJob;
    }

    @Override
    public void run(String... args) throws Exception {

        // Charger d'abord company
        jobLauncher.run(
                loadDimCompanyJob,
                new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis())
                        .toJobParameters()
        );

        //  Ensuite department
        jobLauncher.run(
                loadDimDepartmentJob,
                new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis() + 1)
                        .toJobParameters()
        );

        jobLauncher.run(
                loadDimUserJob,
                new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis() + 1)
                .toJobParameters()
        );
    }
}