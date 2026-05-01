package com.spring_batch.RestErp.rh.batch.writer.itemWriter;

import com.spring_batch.RestErp.rh.dto.fact.FactJobOffer;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class FactJobOfferItemWriter implements ItemWriter<FactJobOffer> {

    private final JdbcTemplate jdbcTemplate;

    private final Map<LocalDate, Integer> dateKeyCache = new HashMap<>();
    private final Map<String, Integer> jobOfferKeyCache = new HashMap<>();
    private final Map<String, Integer> userKeyCache = new HashMap<>();
    private final Map<Long, Integer> companyKeyCache = new HashMap<>();
    private final Map<String, Integer> departmentKeyCache = new HashMap<>();

    public FactJobOfferItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends FactJobOffer> chunk) {
        for (FactJobOffer item : chunk) {
            System.out.println("postingDate raw = " + item.getPostingDate());
            System.out.println("postingDate localDate = " +
                    (item.getPostingDate() != null ? item.getPostingDate().toLocalDate() : null));
            Integer postingDateKey = getDateKey(
                    item.getPostingDate() != null ? item.getPostingDate().toLocalDate() : null
            );


            Integer jobOfferKey = getJobOfferKey(item.getCompanyId(), item.getJobOfferId());
            Integer createdByUserKey = getUserKey(item.getCompanyId(), item.getSubmittedUserId());
            Integer companyKey = getCompanyKey(item.getCompanyId());
            Integer departmentKey = getDepartmentKeyFromUser(item.getCompanyId(), item.getSubmittedUserId());
            if (departmentKey == null) {
                departmentKey = getUnknownDepartmentKey(item.getCompanyId());
            }
            if (postingDateKey == null
                    || jobOfferKey == null
                    || createdByUserKey == null
                    || companyKey == null
                    ) {

                System.out.println("SKIP fact_job_offer | jobOfferId=" + item.getJobOfferId()
                        + " | companyId=" + item.getCompanyId()
                        + " | submittedUserId=" + item.getSubmittedUserId()

                        + " | dateKey=" + postingDateKey
                        + " | jobOfferKey=" + jobOfferKey
                        + " | userKey=" + createdByUserKey
                        + " | companyKey=" + companyKey
                        + " | departmentKey=" + departmentKey);

                continue;
            }

            item.setPostingDateKey(postingDateKey);
            item.setJobOfferKey(jobOfferKey);
            item.setCreatedByUserKey(createdByUserKey);
            item.setCompanyKey(companyKey);
            item.setDepartmentKey(departmentKey);

            jdbcTemplate.update("""
                INSERT INTO public.fact_job_offer (
                    posting_date_key,
                    job_offer_key,
                    created_by_user_key,
                    company_key,
                    department_key,
                    status,
                    job_offers_count,
                    is_active_flag
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (
                    posting_date_key,
                    job_offer_key,
                    created_by_user_key,
                    company_key,
                    department_key
                )
                DO UPDATE SET
                    status = EXCLUDED.status,
                    job_offers_count = EXCLUDED.job_offers_count,
                    is_active_flag = EXCLUDED.is_active_flag
            """,
                    item.getPostingDateKey(),
                    item.getJobOfferKey(),
                    item.getCreatedByUserKey(),
                    item.getCompanyKey(),
                    item.getDepartmentKey(),
                    item.getStatus(),
                    item.getJobOffersCount(),
                    item.getIsActiveFlag()
            );
        }
    }

    private Integer getDateKey(LocalDate monthDate) {
        if (monthDate == null) {
            return null;
        }

        if (dateKeyCache.containsKey(monthDate)) {
            return dateKeyCache.get(monthDate);
        }

        Integer dateKey = jdbcTemplate.query("""
        SELECT date_key
        FROM dim_date
        WHERE full_date = ?
        LIMIT 1
    """,
                rs -> rs.next() ? rs.getInt("date_key") : null,
                java.sql.Date.valueOf(monthDate) // 🔥 FIX ICI
        );

        if (dateKey != null) {
            dateKeyCache.put(monthDate, dateKey);
        }
        System.out.println("Searching dim_date for full_date = " + monthDate);
        return dateKey;
    }

    private Integer getJobOfferKey(Long companyId, Long jobOfferId) {
        if (companyId == null || jobOfferId == null) return null;

        String cacheKey = companyId + "|" + jobOfferId;

        if (jobOfferKeyCache.containsKey(cacheKey)) {
            return jobOfferKeyCache.get(cacheKey);
        }

        Integer jobOfferKey = jdbcTemplate.query("""
            SELECT job_offer_key
            FROM public.dim_job_offer
            WHERE company_id = ?
              AND job_offer_id = ?
              AND is_current = TRUE
            ORDER BY job_offer_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("job_offer_key") : null, companyId, jobOfferId);

        if (jobOfferKey != null) {
            jobOfferKeyCache.put(cacheKey, jobOfferKey);
        }

        return jobOfferKey;
    }

    private Integer getUserKey(Long companyId, Long userId) {
        if (companyId == null || userId == null) return null;

        String cacheKey = companyId + "|" + userId;

        if (userKeyCache.containsKey(cacheKey)) {
            return userKeyCache.get(cacheKey);
        }

        Integer userKey = jdbcTemplate.query("""
            SELECT user_key
            FROM public.dim_user
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
        if (companyId == null) return null;

        if (companyKeyCache.containsKey(companyId)) {
            return companyKeyCache.get(companyId);
        }

        Integer companyKey = jdbcTemplate.query("""
            SELECT company_key
            FROM public.dim_company
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

    private Integer getDepartmentKeyFromUser(Long companyId, Long userId) {
        if (companyId == null || userId == null) return null;

        String cacheKey = companyId + "|" + userId;

        if (departmentKeyCache.containsKey(cacheKey)) {
            return departmentKeyCache.get(cacheKey);
        }

        Integer departmentKey = jdbcTemplate.query("""
        SELECT department_key
        FROM public.dim_user
        WHERE company_id = ?
          AND user_id = ?
          AND is_current = TRUE
        ORDER BY user_key DESC
        LIMIT 1
    """, rs -> rs.next() ? (Integer) rs.getObject("department_key") : null, companyId, userId);

        if (departmentKey != null) {
            departmentKeyCache.put(cacheKey, departmentKey);
        }

        return departmentKey;
    }
    private Integer getUnknownDepartmentKey(Long companyId) {
        return jdbcTemplate.query("""
        SELECT department_key
        FROM public.dim_department
        WHERE company_id = ?
          AND department_id = -1
          AND is_current = TRUE
        LIMIT 1
    """, rs -> rs.next() ? rs.getInt("department_key") : null, companyId);
    }

}