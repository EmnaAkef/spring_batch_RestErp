package com.spring_batch.RestErp.rh.batch.reader.item;

import com.spring_batch.RestErp.rh.dto.source.DailyShiftTemplateSource;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DailyShiftTemplateRowMapper implements RowMapper<DailyShiftTemplateSource> {

    @Override
    public DailyShiftTemplateSource mapRow(ResultSet rs, int rowNum) throws SQLException {
        DailyShiftTemplateSource dailyShift = new DailyShiftTemplateSource();

        dailyShift.setDailyShiftId(rs.getLong("daily_shift_id"));
        dailyShift.setCompanyId(rs.getLong("company_id"));
        dailyShift.setSchemaName(rs.getString("schema_name"));

        if (rs.getObject("weekly_template_id") != null) {
            dailyShift.setWeeklyTemplateId(rs.getLong("weekly_template_id"));
        } else {
            dailyShift.setWeeklyTemplateId(null);
        }

        if (rs.getObject("user_id") != null) {
            dailyShift.setUserId(rs.getLong("user_id"));
        } else {
            dailyShift.setUserId(null);
        }

        if (rs.getObject("dayofweek") != null) {
            dailyShift.setDayOfWeek(rs.getInt("dayofweek"));
        } else {
            dailyShift.setDayOfWeek(null);
        }

        if (rs.getObject("approve") != null) {
            dailyShift.setApprove(rs.getBoolean("approve"));
        } else {
            dailyShift.setApprove(null);
        }

        if (rs.getObject("generalcheckinstate") != null) {
            dailyShift.setGeneralCheckinState(rs.getInt("generalcheckinstate"));
        } else {
            dailyShift.setGeneralCheckinState(null);
        }

        if (rs.getObject("generalcheckoutstate") != null) {
            dailyShift.setGeneralCheckoutState(rs.getInt("generalcheckoutstate"));
        } else {
            dailyShift.setGeneralCheckoutState(null);
        }

        if (rs.getObject("workinghours") != null) {
            dailyShift.setWorkingHours(rs.getDouble("workinghours"));
        } else {
            dailyShift.setWorkingHours(null);
        }

        Timestamp ts = rs.getTimestamp("source_timestamp");
        if (ts != null) {
            dailyShift.setTimestamp(ts.toLocalDateTime());
        } else {
            dailyShift.setTimestamp(null);
        }

        return dailyShift;
    }
}