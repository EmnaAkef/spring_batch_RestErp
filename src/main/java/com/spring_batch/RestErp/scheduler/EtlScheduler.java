package com.spring_batch.RestErp.scheduler;

import com.spring_batch.RestErp.runner.EtlJobOrchestrator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
@ConditionalOnProperty(prefix = "etl.scheduler", name = "enabled", havingValue = "true")
public class EtlScheduler {

    private static final Logger log = LoggerFactory.getLogger(EtlScheduler.class);

    private final EtlJobOrchestrator etlJobOrchestrator;
    private final AtomicBoolean running = new AtomicBoolean(false);

    public EtlScheduler(EtlJobOrchestrator etlJobOrchestrator) {
        this.etlJobOrchestrator = etlJobOrchestrator;
    }

    @Scheduled(cron = "${etl.scheduler.cron}", zone = "${etl.scheduler.zone:Europe/Paris}")
    public void scheduleEtl() {
        if (!running.compareAndSet(false, true)) {
            log.warn("A previous ETL execution is still running. This schedule tick is skipped.");
            return;
        }

        try {
            etlJobOrchestrator.runDefaultPipeline("scheduler");
        } catch (Exception exception) {
            log.error("Scheduled ETL execution failed", exception);
        } finally {
            running.set(false);
        }
    }
}
