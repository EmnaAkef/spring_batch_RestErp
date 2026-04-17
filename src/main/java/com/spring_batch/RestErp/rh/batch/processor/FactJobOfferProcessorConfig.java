package com.spring_batch.RestErp.rh.batch.processor;

import com.spring_batch.RestErp.rh.dto.fact.FactJobOffer;
import com.spring_batch.RestErp.rh.dto.source.FactJobOfferSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactJobOfferProcessorConfig {

    @Bean
    public ItemProcessor<FactJobOfferSource, FactJobOffer> factJobOfferProcessor() {
        return source -> {
            if (source == null || source.getPostingDate() == null || source.getJobOfferId() == null) {
                return null;
            }

            FactJobOffer fact = new FactJobOffer();

            fact.setStatus(cleanText(source.getStatus()));
            fact.setJobOffersCount(1);
            fact.setIsActiveFlag(isActiveStatus(source.getStatus()) ? 1 : 0);

            // champs techniques ETL
            fact.setCompanyId(source.getCompanyId());
            fact.setJobOfferId(source.getJobOfferId());
            fact.setSubmittedUserId(source.getSubmittedUserId());
            fact.setPostingDate(source.getPostingDate());

            return fact;
        };
    }

    private String cleanText(String value) {
        if (value == null) {
            return null;
        }
        String cleaned = value.trim();
        return cleaned.isEmpty() ? null : cleaned;
    }

    private boolean isActiveStatus(String status) {
        if (status == null) {
            return false;
        }

        String s = status.trim().toUpperCase();

        return !s.equals("CLOSED")
                && !s.equals("EXPIRED")
                && !s.equals("INACTIVE")
                && !s.equals("ARCHIVED");
    }
}