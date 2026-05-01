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
            if (source == null) {
                return null;
            }

            FactJobOffer fact = new FactJobOffer();

            fact.setCompanyId(source.getCompanyId());
            fact.setJobOfferId(source.getJobOfferId());
            fact.setSubmittedUserId(source.getSubmittedUserId());
            fact.setPostingDate(source.getPostingDate());

            fact.setStatus(toUpper(cleanText(source.getStatus())));
            fact.setJobOffersCount(1);
            fact.setIsActiveFlag(isActiveStatus(source.getStatus()) ? 1 : 0);

            return fact;
        };
    }

    private String cleanText(String value) {
        if (value == null) return null;
        String cleaned = value.trim();
        return cleaned.isEmpty() ? null : cleaned;
    }

    private String toUpper(String value) {
        return value == null ? null : value.toUpperCase();
    }

    private boolean isActiveStatus(String status) {
        if (status == null) return false;

        String s = status.trim().toUpperCase();

        return !s.equals("CLOSED")
                && !s.equals("EXPIRED")
                && !s.equals("INACTIVE")
                && !s.equals("ARCHIVED");
    }
}