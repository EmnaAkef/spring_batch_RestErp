package com.spring_batch.RestErp.rh.batch.reader.itemReader;

import com.spring_batch.RestErp.rh.dto.source.FactAttendanceShiftSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AttendanceShiftItemReader implements ItemReader<FactAttendanceShiftSource> {

    private final JdbcTemplate jdbcTemplate;
    private final List<FactAttendanceShiftSource> rows = new ArrayList<>();
    private int nextIndex = 0;
    private boolean loaded = false;

    public AttendanceShiftItemReader(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public FactAttendanceShiftSource read() {
        if (!loaded) {
            loadRows();
            loaded = true;
        }

        if (nextIndex < rows.size()) {
            return rows.get(nextIndex++);
        }

        return null;
    }

    private void loadRows() {

        List<Map<String, Object>> companies = jdbcTemplate.queryForList("""
            SELECT id, tenant_schema
            FROM public.company
            WHERE tenant_schema IS NOT NULL
              AND archive = false
            ORDER BY id
        """);

        for (Map<String, Object> company : companies) {

            Long companyId = ((Number) company.get("id")).longValue();
            String schemaName = (String) company.get("tenant_schema");

            if (schemaName == null || schemaName.isBlank()) {
                continue;
            }

            String sql = """
                SELECT
                    pts.timestamp::date AS attendance_date,
                    pts.user_id,
                    u.department_id,
                    pts.shift_id,

                    pts.check_in_state,
                    pts.check_out_state,

                    pts.check_in_time,
                    pts.check_out_time,

                    ds.workinghours AS working_hours_planned

                FROM %s.part_time_shift pts

                LEFT JOIN %s.users_table u
                    ON u.user_id = pts.user_id

                LEFT JOIN %s.daily_shift ds
                    ON ds.id = pts.shift_id

                WHERE pts.user_id IS NOT NULL
                  AND pts.timestamp IS NOT NULL
                  AND u.department_id IS NOT NULL
            """.formatted(schemaName, schemaName, schemaName);

            List<FactAttendanceShiftSource> result = jdbcTemplate.query(sql, (rs, rowNum) -> {
                FactAttendanceShiftSource row = new FactAttendanceShiftSource();

                Date attendanceDate = rs.getDate("attendance_date");
                if (attendanceDate != null) {
                    row.setAttendanceDate(attendanceDate.toLocalDate());
                } else {
                    row.setAttendanceDate(null);
                }

                if (rs.getObject("user_id") != null) {
                    row.setUserId(rs.getLong("user_id"));
                } else {
                    row.setUserId(null);
                }

                row.setCompanyId(companyId);

                if (rs.getObject("department_id") != null) {
                    row.setDepartmentId(rs.getLong("department_id"));
                } else {
                    row.setDepartmentId(null);
                }

                if (rs.getObject("shift_id") != null) {
                    row.setShiftTemplateId(rs.getLong("shift_id"));
                } else {
                    row.setShiftTemplateId(null);
                }

                // V1 : workstatus pas encore exploité
                row.setWorkstatusId(null);

                if (rs.getObject("check_in_state") != null) {
                    row.setCheckInState(rs.getInt("check_in_state"));
                } else {
                    row.setCheckInState(null);
                }

                if (rs.getObject("check_out_state") != null) {
                    row.setCheckOutState(rs.getInt("check_out_state"));
                } else {
                    row.setCheckOutState(null);
                }

                Time checkInTime = rs.getTime("check_in_time");
                if (checkInTime != null) {
                    row.setCheckInTime(checkInTime.toLocalTime());
                } else {
                    row.setCheckInTime(null);
                }

                Time checkOutTime = rs.getTime("check_out_time");
                if (checkOutTime != null) {
                    row.setCheckOutTime(checkOutTime.toLocalTime());
                } else {
                    row.setCheckOutTime(null);
                }

                if (rs.getObject("working_hours_planned") != null) {
                    row.setWorkingHoursPlanned(rs.getDouble("working_hours_planned"));
                } else {
                    row.setWorkingHoursPlanned(null);
                }

                // calcul simple des heures réelles
                Double workingHoursActual = calculateWorkingHoursActual(row.getCheckInTime(), row.getCheckOutTime());
                row.setWorkingHoursActual(workingHoursActual);

                // calcul simple des heures sup
                Double overtimeHours = calculateOvertimeHours(workingHoursActual, row.getWorkingHoursPlanned());
                row.setOvertimeHours(overtimeHours);

                return row;
            });

            rows.addAll(result);
        }
    }

    private Double calculateWorkingHoursActual(LocalTime checkIn, LocalTime checkOut) {
        if (checkIn == null || checkOut == null) {
            return null;
        }

        long minutes = java.time.Duration.between(checkIn, checkOut).toMinutes();
        if (minutes < 0) {
            return null;
        }

        return minutes / 60.0;
    }

    private Double calculateOvertimeHours(Double actual, Double planned) {
        if (actual == null || planned == null) {
            return null;
        }

        double overtime = actual - planned;
        return overtime > 0 ? overtime : 0.0;
    }
}