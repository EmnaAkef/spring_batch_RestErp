package com.spring_batch.RestErp.rh.batch.processor;

import com.spring_batch.RestErp.rh.dto.fact.FactAttendanceShift;
import com.spring_batch.RestErp.rh.dto.source.FactAttendanceShiftSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AttendanceShiftProcessorConfig {

    @Bean
    public ItemProcessor<FactAttendanceShiftSource, FactAttendanceShift> attendanceShiftProcessor() {
        return source -> {

            if (source == null
                    || source.getAttendanceDate() == null
                    || source.getUserId() == null
                    || source.getCompanyId() == null
                    || source.getDepartmentId() == null) {
                return null;
            }

            FactAttendanceShift target = new FactAttendanceShift();

            target.setAttendanceDate(source.getAttendanceDate());
            target.setDateKey(null);

            target.setUserId(source.getUserId());
            target.setUserKey(null);

            target.setCompanyId(source.getCompanyId());
            target.setCompanyKey(null);

            target.setDepartmentId(source.getDepartmentId());
            target.setDepartmentKey(null);

            target.setWorkstatusId(source.getWorkstatusId());
            target.setWorkstatusKey(null);

            target.setShiftTemplateId(source.getShiftTemplateId());
            target.setShiftTemplateKey(null);

            target.setScheduledShiftCount(1);

            boolean isPresent = isPresentComplete(source.getCheckInState(), source.getCheckOutState());
            boolean isAbsent = isAbsentComplete(source.getCheckInState(), source.getCheckOutState());

            target.setPresentShiftCount(isPresent ? 1 : 0);
            target.setAbsentShiftCount(isAbsent ? 1 : 0);

            // V1 : pas encore de vraie logique de retard
            target.setLateCheckinCount(0);

            target.setWorkingHoursPlanned(source.getWorkingHoursPlanned());
            target.setWorkingHoursActual(source.getWorkingHoursActual());
            target.setOvertimeHours(source.getOvertimeHours());

            return target;
        };
    }

    private boolean isPresentComplete(Integer checkInState, Integer checkOutState) {
        return checkInState != null
                && checkOutState != null
                && checkInState == 0
                && checkOutState == 0;
    }

    private boolean isAbsentComplete(Integer checkInState, Integer checkOutState) {
        return checkInState != null
                && checkOutState != null
                && checkInState == 1
                && checkOutState == 1;
    }
}