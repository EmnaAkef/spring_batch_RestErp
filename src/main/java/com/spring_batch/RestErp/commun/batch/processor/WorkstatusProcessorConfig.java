package com.spring_batch.RestErp.commun.batch.processor;

import com.spring_batch.RestErp.commun.dto.dim.DimWorkstatus;
import com.spring_batch.RestErp.commun.dto.source.WorkstatusSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class WorkstatusProcessorConfig {

    @Bean
    public ItemProcessor<WorkstatusSource, DimWorkstatus> workstatusProcessor() {
        return source -> {

            if (source == null || source.getWorkstatusId() == null) {
                return null;
            }

            String cleanedStatusLabel = cleanText(source.getStatusLabel());

            if (cleanedStatusLabel == null) {
                return null;
            }

            DimWorkstatus target = new DimWorkstatus();

            target.setWorkstatusId(source.getWorkstatusId());
            target.setCompanyId(source.getCompanyId());
            target.setCompanyKey(null);

            target.setStatusLabel(cleanedStatusLabel);
            target.setPosition(source.getPosition());
            target.setArchive(source.getArchive());

            target.setEffectiveFrom(LocalDateTime.now());
            target.setEffectiveTo(null);
            target.setIsCurrent(true);

            return target;
        };
    }

    /**
     * Nettoyage des champs texte :
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
}