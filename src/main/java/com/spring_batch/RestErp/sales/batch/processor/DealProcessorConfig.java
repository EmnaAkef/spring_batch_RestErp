package com.spring_batch.RestErp.sales.batch.processor;

import com.spring_batch.RestErp.sales.dto.fact.FactDeal;
import com.spring_batch.RestErp.sales.dto.source.FactDealSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DealProcessorConfig {

    @Bean
    public ItemProcessor<FactDealSource, FactDeal> dealProcessor() {
        return source -> {

            if (source == null || source.getDealId() == null) {
                return null;
            }

            FactDeal target = new FactDeal();

            target.setDealId(source.getDealId());

            target.setCompanyId(source.getCompanyId());
            target.setCompanyKey(null);

            if (source.getCloseDate() != null) {
                target.setCloseDate(source.getCloseDate().toLocalDate());
            } else {
                target.setCloseDate(null);
            }
            target.setCloseDateKey(null);

            target.setCustomerId(source.getCustomerId());
            target.setCustomerKey(null);

            target.setOwnerUserId(source.getOwnerUserId());
            target.setOwnerUserKey(null);

            target.setWorkstatusId(source.getWorkstatusId());
            target.setWorkstatusKey(null);

            target.setDealValue(source.getDealValue());
            target.setDealCount(1);

            target.setIsClosed(source.getCloseDate() != null);
            target.setIsArchived(source.getArchived());

            return target;
        };
    }
}