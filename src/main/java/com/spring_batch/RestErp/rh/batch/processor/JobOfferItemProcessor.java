package com.spring_batch.RestErp.rh.batch.processor;

import com.spring_batch.RestErp.rh.dto.dim.DimJobOffer;
import com.spring_batch.RestErp.rh.dto.source.JobOfferSource;
import org.springframework.batch.item.ItemProcessor;

import java.sql.Timestamp;
import java.time.Instant;

public class JobOfferItemProcessor implements ItemProcessor<JobOfferSource, DimJobOffer> {

    @Override
    public DimJobOffer process(JobOfferSource source) {
        if (source == null) {
            return null;
        }

        DimJobOffer dim = new DimJobOffer();

        dim.setJobOfferId(source.getId());
        dim.setJobTitle(capitalizeFirst(cleanText(source.getJobTitle())));
        dim.setEmploymentType(toUpper(cleanText(source.getEmploymentType())));
        dim.setLocation(capitalizeFirst(cleanText(source.getLocation())));
        dim.setPostingDate(source.getPostingDate());
        dim.setExpiryDate(source.getExpiryDate());
        dim.setRequiredExperienceYears(source.getRequiredExperienceYears());
        dim.setSalaryRangeMin(source.getSalaryRangeMin());
        dim.setSalaryRangeMax(source.getSalaryRangeMax());
        dim.setStatus(toUpper(cleanText(source.getStatus())));
        dim.setSubmittedUserId(source.getSubmittedUserId());

        dim.setEffectiveFrom(Timestamp.from(Instant.now()));
        dim.setEffectiveTo(null);
        dim.setIsCurrent(true);

        return dim;
    }

    private String cleanText(String value) {
        if (value == null) {
            return null;
        }

        String cleaned = value.trim();
        return cleaned.isEmpty() ? null : cleaned;
    }

    private String toUpper(String value) {
        return value == null ? null : value.toUpperCase();
    }

    private String capitalizeFirst(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String lower = value.toLowerCase();
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }
}