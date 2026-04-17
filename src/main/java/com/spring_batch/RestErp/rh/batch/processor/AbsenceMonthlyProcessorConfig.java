package com.spring_batch.RestErp.rh.batch.processor;

import com.spring_batch.RestErp.rh.dto.fact.FactAbsenceMonthly;
import com.spring_batch.RestErp.rh.dto.source.FactAbsenceMonthlySource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AbsenceMonthlyProcessorConfig {

    @Bean
    public ItemProcessor<FactAbsenceMonthlySource, FactAbsenceMonthly> absenceMonthlyProcessor() {
        return source -> {

            if (source == null
                    || source.getMonthDate() == null
                    || source.getCompanyId() == null
                    || source.getDepartmentId() == null
                    || source.getScheduledShiftsCount() == null
                    || source.getScheduledShiftsCount() == 0) {
                return null;
            }

            FactAbsenceMonthly target = new FactAbsenceMonthly();

            target.setMonthDate(source.getMonthDate());
            target.setMonthDateKey(null);

            target.setCompanyId(source.getCompanyId());
            target.setCompanyKey(null);

            target.setDepartmentId(source.getDepartmentId());
            target.setDepartmentKey(null);

            target.setScheduledShiftsCount(source.getScheduledShiftsCount());
            target.setAbsentShiftsCount(source.getAbsentShiftsCount());
            target.setAbsenceRatePct(source.getAbsenceRatePct());

            return target;
        };
    }
}