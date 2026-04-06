package com.spring_batch.RestErp.finance.batch.processor;

import com.spring_batch.RestErp.finance.dto.dim.DimVendor;
import com.spring_batch.RestErp.finance.dto.source.VendorSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Locale;

@Configuration
public class VendorProcessorConfig {

    @Bean
    public ItemProcessor<VendorSource, DimVendor> vendorProcessor() {
        return source -> {

            if (source == null || source.getVendorId() == null) {
                return null;
            }

            String cleanedVendorName = cleanText(source.getVendorName());
            if (cleanedVendorName == null) {
                return null;
            }

            DimVendor target = new DimVendor();

            target.setVendorId(source.getVendorId());
            target.setCompanyId(source.getCompanyId());
            target.setCompanyKey(null);

            String cleanedIndustry = cleanIndustry(source.getIndustry());

            target.setVendorName(cleanedVendorName);
            target.setIndustry(cleanedIndustry);

            target.setEffectiveFrom(LocalDateTime.now());
            target.setEffectiveTo(null);
            target.setIsCurrent(true);

            return target;
        };
    }

    /**
     * Nettoyage standard des champs texte :
     * - trim
     * - suppression des espaces multiples
     * - null si vide
     */
    private String cleanText(String value) {
        if (value == null) {
            return null;
        }

        String cleaned = value.trim().replaceAll("\\s+", " ");
        return cleaned.isEmpty() ? null : cleaned;
    }

    /**
     * Nettoyage spécifique industry
     */
    private String cleanIndustry(String industry) {
        return cleanText(industry);
    }
}