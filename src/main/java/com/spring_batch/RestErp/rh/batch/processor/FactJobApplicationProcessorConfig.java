package com.spring_batch.RestErp.rh.batch.processor;

import com.spring_batch.RestErp.rh.dto.fact.FactJobApplication;
import com.spring_batch.RestErp.rh.dto.source.FactJobApplicationSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactJobApplicationProcessorConfig {

    @Bean
    public ItemProcessor<FactJobApplicationSource, FactJobApplication> factJobApplicationProcessor() {
        return source -> {
            if (source == null || source.getSubmissionDate() == null || source.getJobOfferId() == null) {
                return null;
            }

            FactJobApplication fact = new FactJobApplication();

            fact.setApplicationStatus(cleanText(source.getApplicationStatus()));
            fact.setApplicationsCount(1);
            fact.setIsHiredFlag(Boolean.TRUE.equals(source.getIsHired()) ? 1 : 0);

            fact.setCompanyId(source.getCompanyId());
            fact.setJobOfferId(source.getJobOfferId());
            fact.setSubmissionDate(source.getSubmissionDate());

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
}