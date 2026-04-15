package com.spring_batch.RestErp.finance.batch.processor;

import com.spring_batch.RestErp.finance.dto.fact.FactChartBalanceSnapshot;
import com.spring_batch.RestErp.finance.dto.source.ChartBalanceSnapshotSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChartBalanceSnapshotProcessorConfig {

    @Bean
    public ItemProcessor<ChartBalanceSnapshotSource, FactChartBalanceSnapshot> chartBalanceSnapshotProcessor() {
        return source -> {
            if (source == null || source.getChartId() == null || source.getTimestamp() == null) {
                return null;
            }

            FactChartBalanceSnapshot fact = new FactChartBalanceSnapshot();

            fact.setCloseBalanceDebit(source.getCloseBalanceDebit());
            fact.setCloseBalanceCredit(source.getCloseBalanceCredit());
            fact.setOpenBalanceDebit(source.getOpenBalanceDebit());
            fact.setOpenBalanceCredit(source.getOpenBalanceCredit());
            fact.setTotalMovementDebit(source.getTotalMovementDebit());
            fact.setTotalMovementCredit(source.getTotalMovementCredit());

            return fact;
        };
    }
}