package com.spring_batch.RestErp.rh.batch.processor;

import com.spring_batch.RestErp.rh.dto.dim.DimDailyShiftTemplate;
import com.spring_batch.RestErp.rh.dto.source.DailyShiftTemplateSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DailyShiftTemplateProcessorConfig {

    @Bean
    public ItemProcessor<DailyShiftTemplateSource, DimDailyShiftTemplate> dailyShiftTemplateProcessor() {
        return source -> {

            if (source == null || source.getDailyShiftId() == null) {
                return null;
            }

            DimDailyShiftTemplate target = new DimDailyShiftTemplate();

            target.setDailyShiftId(source.getDailyShiftId());
            target.setCompanyId(source.getCompanyId());
            target.setCompanyKey(null);

            target.setWeeklyTemplateId(source.getWeeklyTemplateId());
            target.setWeeklyTemplateKey(null);

            target.setDayOfWeek(source.getDayOfWeek());
            target.setApprove(source.getApprove());
            target.setGeneralCheckinState(source.getGeneralCheckinState());
            target.setGeneralCheckoutState(source.getGeneralCheckoutState());
            target.setWorkingHours(source.getWorkingHours());

            target.setEffectiveFrom(LocalDateTime.now());
            target.setEffectiveTo(null);
            target.setIsCurrent(true);

            return target;
        };
    }
}