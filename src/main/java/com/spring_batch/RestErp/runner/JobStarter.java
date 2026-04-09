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
    private final Job loadDimJobOfferJob;
    private final Job loadDimChartAccountJob;
    private final Job loadDimWeeklyShiftTemplateJob;
    private final Job loadDimDailyShiftTemplateJob;
    private final Job loadFactSalesLineJob;


    private final Job loadFactChartBalanceSnapshotJob;

    public JobStarter(
            JobLauncher jobLauncher,
            @Qualifier("loadDimCompanyJob") Job loadDimCompanyJob,
            @Qualifier("loadDimDepartmentJob") Job loadDimDepartmentJob,
            @Qualifier("loadDimUserJob") Job loadDimUserJob,
            @Qualifier("loadDimCustomerJob") Job loadDimCustomerJob,
            @Qualifier("loadDimWorkstatusJob") Job loadDimWorkstatusJob,
            @Qualifier("loadDimProductJob") Job loadDimProductJob,
            @Qualifier("loadDimVendorJob") Job loadDimVendorJob,
            @Qualifier("loadDimJobOfferJob") Job loadDimJobOfferJob,
            @Qualifier("loadDimChartAccountJob") Job loadDimChartAccountJob,
            @Qualifier("loadDimWeeklyShiftTemplateJob") Job loadDimWeeklyShiftTemplateJob,
            @Qualifier("loadDimDailyShiftTemplateJob") Job loadDimDailyShiftTemplateJob,
<<<<<<< HEAD



            @Qualifier("loadFactChartBalanceSnapshotJob") Job loadFactChartBalanceSnapshotJob) {
=======
            @Qualifier("loadFactSalesLineJob") Job loadFactSalesLineJob) {
>>>>>>> 6e2755369b8e19712e6c43c7283c49e38230809d

        this.jobLauncher = jobLauncher;
        this.loadDimCompanyJob = loadDimCompanyJob;
        this.loadDimDepartmentJob = loadDimDepartmentJob;
        this.loadDimUserJob = loadDimUserJob;
        this.loadDimCustomerJob = loadDimCustomerJob;
        this.loadDimWorkstatusJob = loadDimWorkstatusJob;
        this.loadDimProductJob = loadDimProductJob;
        this.loadDimVendorJob = loadDimVendorJob;
        this.loadDimJobOfferJob = loadDimJobOfferJob;
        this.loadDimChartAccountJob = loadDimChartAccountJob;
        this.loadDimWeeklyShiftTemplateJob = loadDimWeeklyShiftTemplateJob;
        this.loadDimDailyShiftTemplateJob = loadDimDailyShiftTemplateJob;
<<<<<<< HEAD


        this.loadFactChartBalanceSnapshotJob = loadFactChartBalanceSnapshotJob;
=======
        this.loadFactSalesLineJob = loadFactSalesLineJob;
>>>>>>> 6e2755369b8e19712e6c43c7283c49e38230809d
    }

    @Override
    public void run(String... args) throws Exception {

        long baseTime = System.currentTimeMillis();

//        jobLauncher.run(
//                loadDimCompanyJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime)
//                        .toJobParameters()
//        );
//
//        jobLauncher.run(
//                loadDimDepartmentJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 1)
//                        .toJobParameters()
//        );
//
//        jobLauncher.run(
//                loadDimUserJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 2)
//                        .toJobParameters()
//        );
//
//        jobLauncher.run(
//                loadDimCustomerJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 3)
//                        .toJobParameters()
//        );
//
//        jobLauncher.run(
//                loadDimWorkstatusJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 4)
//                        .toJobParameters()
//        );
//
//        jobLauncher.run(
//                loadDimProductJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 5)
//                        .toJobParameters()
//        );
//
//        jobLauncher.run(
//                loadDimVendorJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 6)
//                        .toJobParameters()
//        );
//
//        jobLauncher.run(
//                loadDimJobOfferJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 7)
//                        .toJobParameters()
//        );
//
//        jobLauncher.run(
//                loadDimChartAccountJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 8)
//                        .toJobParameters()
//        );
//
//        jobLauncher.run(
//                loadDimWeeklyShiftTemplateJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 9)
//                        .toJobParameters()
//        );
<<<<<<< HEAD

//        jobLauncher.run(
//                loadDimDailyShiftTemplateJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 10)
//                        .toJobParameters()
//        );


        //fact tables
        jobLauncher.run(
                loadFactChartBalanceSnapshotJob,
=======
//
//        jobLauncher.run(
//                loadDimDailyShiftTemplateJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 10)
//                        .toJobParameters()
//        );

        jobLauncher.run(
                loadFactSalesLineJob,
>>>>>>> 6e2755369b8e19712e6c43c7283c49e38230809d
                new JobParametersBuilder()
                        .addLong("time", baseTime + 11)
                        .toJobParameters()
        );
    }
}