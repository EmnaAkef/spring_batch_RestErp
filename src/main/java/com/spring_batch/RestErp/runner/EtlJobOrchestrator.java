package com.spring_batch.RestErp.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtlJobOrchestrator {

    private static final Logger log = LoggerFactory.getLogger(EtlJobOrchestrator.class);

    private final JobLauncher jobLauncher;
    private final List<Job> orderedJobs;

    public EtlJobOrchestrator(
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
            @Qualifier("loadFactSalesFinancialsJob") Job loadFactSalesFinancialsJob,
            @Qualifier("loadFactSalesOrderJob") Job loadFactSalesOrderJob,
            @Qualifier("loadFactInvoiceJob") Job loadFactInvoiceJob,
            @Qualifier("loadFactDealJob") Job loadFactDealJob,
            @Qualifier("loadFactBillJob") Job loadFactBillJob,
            @Qualifier("loadFactAssetJob") Job loadFactAssetJob,
            @Qualifier("loadFactJobApplicationJob") Job loadFactJobApplicationJob,
            @Qualifier("loadFactAbsenceMonthlyJob") Job loadFactAbsenceMonthlyJob,
            @Qualifier("loadFactJobOfferJob") Job loadFactJobOfferJob,
            @Qualifier("loadFactEmployeeHrJob") Job loadFactEmployeeHrJob,
            @Qualifier("loadFactAttendanceShiftJob") Job loadFactAttendanceShiftJob) {

        this.jobLauncher = jobLauncher;
        this.orderedJobs = List.of(
                loadDimCompanyJob,
                loadDimDepartmentJob,
                loadDimUserJob,
                loadDimCustomerJob,
                loadDimWorkstatusJob,
                loadDimProductJob,
                loadDimVendorJob,
                loadDimJobOfferJob,
                loadDimChartAccountJob,
                loadDimWeeklyShiftTemplateJob,
                loadDimDailyShiftTemplateJob,
                loadFactSalesLineJob,
                loadFactChartBalanceSnapshotJob,
                loadFactCashMovementJob,
                loadFactSalesFinancialsJob,
                loadFactSalesOrderJob,
                loadFactInvoiceJob,
                loadFactDealJob,
                loadFactBillJob,
                loadFactAssetJob,
                loadFactJobApplicationJob,
                loadFactAbsenceMonthlyJob,
                loadFactJobOfferJob,
                loadFactEmployeeHrJob,
                loadFactAttendanceShiftJob
        );
    }

    public void runDefaultPipeline(String trigger) throws Exception {
        long baseTime = System.currentTimeMillis();

        for (int i = 0; i < orderedJobs.size(); i++) {
            Job job = orderedJobs.get(i);
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", baseTime + i)
                    .addString("trigger", trigger)
                    .toJobParameters();

            log.info("Starting job {} with trigger={}", job.getName(), trigger);
            JobExecution execution = jobLauncher.run(job, jobParameters);
            log.info("Job {} finished with status={}", job.getName(), execution.getStatus());
        }
    }
}
