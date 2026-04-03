package com.spring_batch.RestErp.commun.batch.processor;

import com.spring_batch.RestErp.commun.dto.dim.DimUser;
import com.spring_batch.RestErp.commun.dto.source.UserSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class UserProcessorConfig {

    @Bean
    public ItemProcessor<UserSource, DimUser> userProcessor() {
        return source -> {

            if (source == null || source.getUserName() == null || source.getUserName().trim().isEmpty()) {
                return null;
            }

            DimUser target = new DimUser();

            target.setUserId(source.getUserId());
            target.setCompanyId(source.getCompanyId());
            target.setDepartmentId(source.getDepartmentId());
            target.setDepartmentName(source.getDepartmentName());
            target.setUserName(source.getUserName().trim());

            String genderLabel = null;
            if (source.getGender() != null) {
                if (source.getGender() == 0) {
                    genderLabel = "F";
                } else if (source.getGender() == 1) {
                    genderLabel = "M";
                }
            }
            target.setGender(genderLabel);

            String typeLabel = null;
            if (source.getType() != null) {
                switch (source.getType()) {
                    case 0 -> typeLabel = "EMPLOYEE";
                    case 1 -> typeLabel = "CONTRACTOR";
                    case 2 -> typeLabel = "OFFBOARDING";
                    case 3 -> typeLabel = "COMPANY_OWNER";
                }
            }
            target.setType(typeLabel);
            target.setActive(source.getActive());
            target.setBirthdate(source.getBirthdate());
            target.setPosition(source.getPosition());
            target.setSalary(source.getSalary());
            target.setTotalHours(source.getTotalHours());

            target.setCompanyKey(null);
            target.setDepartmentKey(null);

            target.setEffectiveFrom(LocalDateTime.now());
            target.setEffectiveTo(null);
            target.setIsCurrent(true);

            return target;
        };
    }
}
