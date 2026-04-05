package com.spring_batch.RestErp.commun.batch.processor;

import com.spring_batch.RestErp.commun.dto.dim.DimCustomer;
import com.spring_batch.RestErp.commun.dto.source.CustomerSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Locale;

@Configuration
public class CustomerProcessorConfig {

    @Bean
    public ItemProcessor<CustomerSource, DimCustomer> customerProcessor() {
        return source -> {

            if (source == null || source.getCustomerId() == null) {
                return null;
            }

            DimCustomer target = new DimCustomer();

            target.setCustomerId(source.getCustomerId());
            target.setCompanyId(source.getCompanyId());
            target.setCompanyKey(null);

            target.setClientCategory(mapClientCategory(source.getClientCategory()));
            target.setType(mapCustomerType(source.getType()));
            target.setStatus(normalizeStatus(source.getStatus()));

            target.setActive(source.getActive());
            target.setCity(normalizeCity(source.getCity()));
            target.setNationality(cleanText(source.getNationality()));
            target.setContactName(cleanText(source.getContactName()));
            target.setOrganizationName(cleanText(source.getOrganizationName()));

            target.setEffectiveFrom(LocalDateTime.now());
            target.setEffectiveTo(null);
            target.setIsCurrent(true);

            return target;
        };
    }

    /**
     * Nettoyage simple des champs texte :
     * - trim
     * - suppression des espaces multiples
     * - retourne null si vide
     */
    private String cleanText(String value) {
        if (value == null) {
            return null;
        }

        String cleaned = value.trim().replaceAll("\\s+", " ");
        return cleaned.isEmpty() ? null : cleaned;
    }

    /**
     * Mapping de clientCategory :
     */
    private String mapClientCategory(Integer clientCategory) {
        if (clientCategory == null) {
            return null;
        }

        return switch (clientCategory) {
            case 0 -> "B2B";
            case 1 -> "B2C";
            default -> "UNKNOWN";
        };
    }

    /**
     * Mapping de type.
     * Comme la signification métier exacte n'est pas encore confirmée,
     * on garde un libellé technique lisible.
     */
    private String mapCustomerType(Integer type) {
        if (type == null) {
            return null;
        }

        return switch (type) {
            case 0 -> "TYPE_0";
            case 1 -> "TYPE_1";
            default -> "UNKNOWN";
        };
    }

    /**
     * Normalisation du status :
     * - trim
     * - uppercase
     */
    private String normalizeStatus(String status) {
        if (status == null) {
            return null;
        }

        return status.trim().toUpperCase(Locale.ROOT);
    }

    private String normalizeCity(String city) {
        if (city == null) {
            return null;
        }

        String normalized = city.trim().replaceAll("\\s+", " ");

        switch (normalized.toLowerCase()) {
            case "lake daniel":
            case "lake danielle":
                return "Lake Daniell";

            case "lake jon":
                return "Lake Jhon";

            default:
                return normalized;
        }
    }

}