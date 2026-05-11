package com.spring_batch.RestErp.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "etl.single", name = "enabled", havingValue = "true")
public class SingleJobRunner implements CommandLineRunner {

    private final EtlJobOrchestrator orchestrator;

    public SingleJobRunner(EtlJobOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @Override
    public void run(String... args) throws Exception {
        orchestrator.runSingleJob("loadFactJobOfferJob", "manual-test");
    }
}