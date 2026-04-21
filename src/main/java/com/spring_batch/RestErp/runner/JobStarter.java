package com.spring_batch.RestErp.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "etl.startup", name = "enabled", havingValue = "true")
public class JobStarter implements CommandLineRunner {

    private final EtlJobOrchestrator etlJobOrchestrator;

    public JobStarter(EtlJobOrchestrator etlJobOrchestrator) {
        this.etlJobOrchestrator = etlJobOrchestrator;
    }

    @Override
    public void run(String... args) throws Exception {
        etlJobOrchestrator.runDefaultPipeline("startup");
    }
}
