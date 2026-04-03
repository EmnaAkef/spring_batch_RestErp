package com.spring_batch.RestErp.commun.batch.Writer.itemWriter;

import com.spring_batch.RestErp.commun.dto.dim.DimUser;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class UserItemWriter implements ItemWriter<DimUser> {

    private final JdbcTemplate jdbcTemplate;

    /*
     * Cache pour améliorer les performances
     */
    private final Map<Long, Integer> companyCache = new HashMap<>();
    private final Map<String, Integer> departmentCache = new HashMap<>();

    public UserItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends DimUser> chunk) {

        for (DimUser item : chunk) {

            // 1️⃣ récupérer company_key
            Integer companyKey = getCompanyKey(item.getCompanyId());

            if (companyKey == null) {
                throw new IllegalStateException(
                        "Company introuvable pour company_id = " + item.getCompanyId()
                );
            }

            item.setCompanyKey(companyKey);

            // 2️⃣ récupérer department_key
            Integer departmentKey = getDepartmentKey(item);
            item.setDepartmentKey(departmentKey);

            Timestamp effectiveFrom = Timestamp.valueOf(item.getEffectiveFrom());

            // 3️⃣ UPDATE (SCD2)
            jdbcTemplate.update("""
                UPDATE dim_user
                SET effective_to = ?, is_current = false
                WHERE user_id = ?
                  AND company_id = ?
                  AND is_current = true
                  AND (
                      COALESCE(department_key, -1) <> COALESCE(?, -1)
                      OR COALESCE(user_name, '') <> COALESCE(?, '')
                      OR COALESCE(gender, '') <> COALESCE(?, '')
                      OR COALESCE(type, '') <> COALESCE(?, '')
                      OR COALESCE(active, false) <> COALESCE(?, false)
                      OR COALESCE(position, '') <> COALESCE(?, '')
                  )
            """,
                    effectiveFrom,
                    item.getUserId(),
                    item.getCompanyId(),
                    item.getDepartmentKey(),
                    item.getUserName(),
                    item.getGender(),
                    item.getType(),
                    item.getActive(),
                    item.getPosition()
            );

            // 4️⃣ INSERT si nouvelle version
            jdbcTemplate.update("""
                INSERT INTO dim_user (
                    user_id,
                    company_id,
                    company_key,
                    department_id,
                    department_key,
                    user_name,
                    gender,
                    type,
                    active,
                    birthdate,
                    position,
                    salary,
                    total_hours,
                    effective_from,
                    effective_to,
                    is_current
                )
                SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, true
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM dim_user
                    WHERE user_id = ?
                      AND company_id = ?
                      AND is_current = true
                      AND COALESCE(department_key, -1) = COALESCE(?, -1)
                      AND COALESCE(user_name, '') = COALESCE(?, '')
                      AND COALESCE(gender, '') = COALESCE(?, '')
                      AND COALESCE(type, '') = COALESCE(?, '')
                      AND COALESCE(active, false) = COALESCE(?, false)
                      AND COALESCE(position, '') = COALESCE(?, '')
                )
            """,
                    item.getUserId(),
                    item.getCompanyId(),
                    item.getCompanyKey(),
                    item.getDepartmentId(),
                    item.getDepartmentKey(),
                    item.getUserName(),
                    item.getGender(),
                    item.getType(),
                    item.getActive(),
                    item.getBirthdate(),
                    item.getPosition(),
                    item.getSalary(),
                    item.getTotalHours(),
                    effectiveFrom,

                    item.getUserId(),
                    item.getCompanyId(),
                    item.getDepartmentKey(),
                    item.getUserName(),
                    item.getGender(),
                    item.getType(),
                    item.getActive(),
                    item.getPosition()
            );
        }
    }

    /*
     * 🔹 Récupération company_key avec cache
     */
    private Integer getCompanyKey(Long companyId) {

        if (companyCache.containsKey(companyId)) {
            return companyCache.get(companyId);
        }

        Integer companyKey = jdbcTemplate.query("""
            SELECT company_key
            FROM dim_company
            WHERE company_id = ?
              AND is_current = true
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt(1) : null, companyId);

        if (companyKey != null) {
            companyCache.put(companyId, companyKey);
        }

        return companyKey;
    }

    /*
     * 🔹 Récupération department_key avec fallback intelligent
     */
    private Integer getDepartmentKey(DimUser item) {

        // 1️⃣ lookup par department_id
        Integer depKey = jdbcTemplate.query("""
            SELECT department_key
            FROM dim_department
            WHERE company_id = ?
              AND department_id = ?
              AND is_current = true
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt(1) : null,
                item.getCompanyId(),
                item.getDepartmentId());

        if (depKey != null) {
            return depKey;
        }

        // 2️⃣ fallback par nom normalisé
        if (item.getDepartmentName() != null) {

            String normalized = item.getDepartmentName()
                    .trim()
                    .replaceAll("\\s+", " ")
                    .toLowerCase();

            String key = item.getCompanyId() + "|" + normalized;

            if (departmentCache.containsKey(key)) {
                return departmentCache.get(key);
            }

            Integer depKeyByName = jdbcTemplate.query("""
                SELECT department_key
                FROM dim_department
                WHERE company_id = ?
                  AND LOWER(TRIM(department_name)) = ?
                  AND is_current = true
                LIMIT 1
            """, rs -> rs.next() ? rs.getInt(1) : null,
                    item.getCompanyId(),
                    normalized);

            if (depKeyByName != null) {
                departmentCache.put(key, depKeyByName);
            }

            return depKeyByName;
        }

        return null;
    }
}
