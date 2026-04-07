package com.spring_batch.RestErp.rh.batch.processor;

import com.spring_batch.RestErp.rh.dto.dim.DimWeeklyShiftTemplate;
import com.spring_batch.RestErp.rh.dto.source.WeeklyShiftTemplateSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class WeeklyShiftTemplateProcessorConfig {

    @Bean
    public ItemProcessor<WeeklyShiftTemplateSource, DimWeeklyShiftTemplate> weeklyShiftTemplateProcessor() {
        return source -> {

            if (source == null || source.getWeeklyTemplateId() == null) {
                return null;
            }

            DimWeeklyShiftTemplate target = new DimWeeklyShiftTemplate();

            target.setWeeklyTemplateId(source.getWeeklyTemplateId());
            target.setCompanyId(source.getCompanyId());
            target.setCompanyKey(null);

            target.setCreatorId(source.getCreatorId());
            target.setCreatorName(cleanText(source.getCreatorName()));
            target.setStartDate(source.getStartDate());
            target.setEndDate(source.getEndDate());
            target.setTemplateName(cleanText(source.getTemplateName()));
            target.setTotalWorkHours(source.getTotalWorkHours());
            target.setType(source.getType());

            target.setEffectiveFrom(LocalDateTime.now());
            target.setEffectiveTo(null);
            target.setIsCurrent(true);

            return target;
        };
    }

    private String cleanText(String value) {
        if (value == null) {
            return null;
        }

        String cleaned = value.trim().replaceAll("\\s+", " ");
        return cleaned.isEmpty() ? null : cleaned;
    }

}