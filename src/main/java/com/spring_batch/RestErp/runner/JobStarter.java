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
    private final Job loadFactCashMovementJob;
    private final Job loadFactSalesFinancialsJob;

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
            @Qualifier("loadFactSalesLineJob") Job loadFactSalesLineJob,
            @Qualifier("loadFactChartBalanceSnapshotJob") Job loadFactChartBalanceSnapshotJob,
            @Qualifier("loadFactCashMovementJob") Job loadFactCashMovementJob,
            @Qualifier("loadFactSalesFinancialsJob") Job loadFactSalesFinancialsJob) {

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
        this.loadFactSalesLineJob = loadFactSalesLineJob;
        this.loadFactChartBalanceSnapshotJob = loadFactChartBalanceSnapshotJob;
        this.loadFactCashMovementJob = loadFactCashMovementJob;
        this.loadFactSalesFinancialsJob = loadFactSalesFinancialsJob;
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

//        jobLauncher.run(
//                loadDimDepartmentJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 1)
//                        .toJobParameters()
//        );

//        jobLauncher.run(
//                loadDimUserJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 2)
//                        .toJobParameters()
//        );

//        jobLauncher.run(
//                loadDimCustomerJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 3)
//                        .toJobParameters()
//        );

//        jobLauncher.run(
//                loadDimWorkstatusJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 4)
//                        .toJobParameters()
//        );

//        jobLauncher.run(
//                loadDimProductJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 5)
//                        .toJobParameters()
//        );

//        jobLauncher.run(
//                loadDimVendorJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 6)
//                        .toJobParameters()
//        );

//        jobLauncher.run(
//                loadDimJobOfferJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 7)
//                        .toJobParameters()
//        );

//        jobLauncher.run(
//                loadDimChartAccountJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 8)
//                        .toJobParameters()
//        );

//        jobLauncher.run(
//                loadDimWeeklyShiftTemplateJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 9)
//                        .toJobParameters()
//        );

//        jobLauncher.run(
//                loadDimDailyShiftTemplateJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 10)
//                        .toJobParameters()
//        );

//        jobLauncher.run(
//                loadFactSalesLineJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 11)
//                        .toJobParameters()
//        );

//        jobLauncher.run(
//                loadFactChartBalanceSnapshotJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 11)
//                        .toJobParameters()
//        ); not done yet

//        jobLauncher.run(
//                loadFactCashMovementJob,
//                new JobParametersBuilder()
//                        .addLong("time", baseTime + 11)
//                        .toJobParameters()
//        );

        jobLauncher.run(
                loadFactSalesFinancialsJob,
                new JobParametersBuilder()
                        .addLong("time", baseTime + 11)
                        .toJobParameters()
        );


    }
}