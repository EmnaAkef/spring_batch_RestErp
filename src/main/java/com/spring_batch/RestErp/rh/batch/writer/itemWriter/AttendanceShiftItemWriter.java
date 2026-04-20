package com.spring_batch.RestErp.rh.batch.writer.itemWriter;

import com.spring_batch.RestErp.rh.dto.fact.FactAttendanceShift;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AttendanceShiftItemWriter implements ItemWriter<FactAttendanceShift> {

    private final JdbcTemplate jdbcTemplate;

    private final Map<LocalDate, Integer> dateKeyCache = new HashMap<>();
    private final Map<String, Integer> userKeyCache = new HashMap<>();
    private final Map<Long, Integer> companyKeyCache = new HashMap<>();
    private final Map<String, Integer> departmentKeyCache = new HashMap<>();
    private final Map<String, Integer> shiftTemplateKeyCache = new HashMap<>();
    private final Map<String, Integer> workstatusKeyCache = new HashMap<>();

    public AttendanceShiftItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends FactAttendanceShift> chunk) {
        for (FactAttendanceShift item : chunk) {

            Integer dateKey = getDateKey(item.getAttendanceDate());
            item.setDateKey(dateKey);

            Integer userKey = getUserKey(item.getCompanyId(), item.getUserId());
            item.setUserKey(userKey);

            Integer companyKey = getCompanyKey(item.getCompanyId());
            item.setCompanyKey(companyKey);

            Integer departmentKey = getDepartmentKey(item.getCompanyId(), item.getDepartmentId());
            item.setDepartmentKey(departmentKey);

            Integer shiftTemplateKey = getShiftTemplateKey(item.getCompanyId(), item.getShiftTemplateId());
            item.setShiftTemplateKey(shiftTemplateKey);

            Integer workstatusKey = getWorkstatusKey(item.getCompanyId(), item.getWorkstatusId());
            item.setWorkstatusKey(workstatusKey);

            if (item.getDateKey() == null
                    || item.getUserKey() == null
                    || item.getCompanyKey() == null
                    || item.getDepartmentKey() == null) {
                continue;
            }

            jdbcTemplate.update("""
                INSERT INTO fact_attendance_shift (
                    date_key,
                    user_key,
                    company_key,
                    department_key,
                    workstatus_key,
                    shift_template_key,
                    scheduled_shift_count,
                    present_shift_count,
                    absent_shift_count,
                    late_checkin_count,
                    working_hours_planned,
                    working_hours_actual,
                    overtime_hours
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (date_key, user_key, shift_template_key)
                DO UPDATE SET
                    company_key = EXCLUDED.company_key,
                    department_key = EXCLUDED.department_key,
                    workstatus_key = EXCLUDED.workstatus_key,
                    scheduled_shift_count = EXCLUDED.scheduled_shift_count,
                    present_shift_count = EXCLUDED.present_shift_count,
                    absent_shift_count = EXCLUDED.absent_shift_count,
                    late_checkin_count = EXCLUDED.late_checkin_count,
                    working_hours_planned = EXCLUDED.working_hours_planned,
                    working_hours_actual = EXCLUDED.working_hours_actual,
                    overtime_hours = EXCLUDED.overtime_hours
            """,
                    item.getDateKey(),
                    item.getUserKey(),
                    item.getCompanyKey(),
                    item.getDepartmentKey(),
                    item.getWorkstatusKey(),
                    item.getShiftTemplateKey(),
                    item.getScheduledShiftCount(),
                    item.getPresentShiftCount(),
                    item.getAbsentShiftCount(),
                    item.getLateCheckinCount(),
                    item.getWorkingHoursPlanned(),
                    item.getWorkingHoursActual(),
                    item.getOvertimeHours()
            );
        }
    }

    private Integer getDateKey(LocalDate attendanceDate) {
        if (attendanceDate == null) {
            return null;
        }

        if (dateKeyCache.containsKey(attendanceDate)) {
            return dateKeyCache.get(attendanceDate);
        }

        Integer dateKey = jdbcTemplate.query("""
            SELECT date_key
            FROM dim_date
            WHERE full_date = ?
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("date_key") : null, attendanceDate);

        if (dateKey != null) {
            dateKeyCache.put(attendanceDate, dateKey);
        }

        return dateKey;
    }

    private Integer getUserKey(Long companyId, Long userId) {
        if (companyId == null || userId == null) {
            return null;
        }

        String cacheKey = companyId + "|" + userId;

        if (userKeyCache.containsKey(cacheKey)) {
            return userKeyCache.get(cacheKey);
        }

        Integer userKey = jdbcTemplate.query("""
            SELECT user_key
            FROM dim_user
            WHERE company_id = ?
              AND user_id = ?
              AND is_current = TRUE
            ORDER BY user_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("user_key") : null, companyId, userId);

        if (userKey != null) {
            userKeyCache.put(cacheKey, userKey);
        }

        return userKey;
    }

    private Integer getCompanyKey(Long companyId) {
        if (companyId == null) {
            return null;
        }

        if (companyKeyCache.containsKey(companyId)) {
            return companyKeyCache.get(companyId);
        }

        Integer companyKey = jdbcTemplate.query("""
            SELECT company_key
            FROM dim_company
            WHERE company_id = ?
              AND is_current = TRUE
            ORDER BY company_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("company_key") : null, companyId);

        if (companyKey != null) {
            companyKeyCache.put(companyId, companyKey);
        }

        return companyKey;
    }

    private Integer getDepartmentKey(Long companyId, Long departmentId) {
        if (companyId == null || departmentId == null) {
            return null;
        }

        String cacheKey = companyId + "|" + departmentId;

        if (departmentKeyCache.containsKey(cacheKey)) {
            return departmentKeyCache.get(cacheKey);
        }

        Integer departmentKey = jdbcTemplate.query("""
            SELECT department_key
            FROM dim_department
            WHERE company_id = ?
              AND department_id = ?
              AND is_current = TRUE
            ORDER BY department_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("department_key") : null, companyId, departmentId);

        if (departmentKey != null) {
            departmentKeyCache.put(cacheKey, departmentKey);
        }

        return departmentKey;
    }

    private Integer getShiftTemplateKey(Long companyId, Long shiftTemplateId) {
        if (companyId == null || shiftTemplateId == null) {
            return null;
        }

        String cacheKey = companyId + "|" + shiftTemplateId;

        if (shiftTemplateKeyCache.containsKey(cacheKey)) {
            return shiftTemplateKeyCache.get(cacheKey);
        }

        Integer shiftTemplateKey = jdbcTemplate.query("""
            SELECT daily_shift_key
            FROM dim_daily_shift_template
            WHERE company_id = ?
              AND daily_shift_id = ?
              AND is_current = TRUE
            ORDER BY daily_shift_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("daily_shift_key") : null, companyId, shiftTemplateId);

        if (shiftTemplateKey != null) {
            shiftTemplateKeyCache.put(cacheKey, shiftTemplateKey);
        }

        return shiftTemplateKey;
    }

    private Integer getWorkstatusKey(Long companyId, Long workstatusId) {
        if (companyId == null || workstatusId == null) {
            return null;
        }

        String cacheKey = companyId + "|" + workstatusId;

        if (workstatusKeyCache.containsKey(cacheKey)) {
            return workstatusKeyCache.get(cacheKey);
        }

        Integer workstatusKey = jdbcTemplate.query("""
            SELECT workstatus_key
            FROM dim_workstatus
            WHERE company_id = ?
              AND workstatus_id = ?
              AND is_current = TRUE
            ORDER BY workstatus_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("workstatus_key") : null, companyId, workstatusId);

        if (workstatusKey != null) {
            workstatusKeyCache.put(cacheKey, workstatusKey);
        }

        return workstatusKey;
    }
}