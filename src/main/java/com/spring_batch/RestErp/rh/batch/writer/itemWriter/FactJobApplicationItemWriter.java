package com.spring_batch.RestErp.rh.batch.writer.itemWriter;

import com.spring_batch.RestErp.rh.dto.fact.FactJobApplication;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class FactJobApplicationItemWriter implements ItemWriter<FactJobApplication> {

    private final JdbcTemplate jdbcTemplate;

    private final Map<LocalDate, Integer> dateKeyCache = new HashMap<>();
    private final Map<Long, Integer> jobOfferKeyCache = new HashMap<>();
    private final Map<Long, Integer> userKeyCache = new HashMap<>();

    public FactJobApplicationItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends FactJobApplication> chunk) {
        for (FactJobApplication item : chunk) {

            Integer submissionDateKey = getDateKey(
                    item.getSubmissionDate() != null ? item.getSubmissionDate().toLocalDate() : null
            );
            if (submissionDateKey == null) {
                throw new IllegalStateException(
                        "submission_date_key introuvable pour submissionDate = " + item.getSubmissionDate()
                );
            }

            Integer jobOfferKey = getJobOfferKey(item.getJobOfferId());
            Integer candidateUserKey = getUserKey(item.getCandidateUserId());

            item.setSubmissionDateKey(submissionDateKey);
            item.setJobOfferKey(jobOfferKey);
            item.setCandidateUserKey(candidateUserKey);

            // Ignore les lignes si une cle obligatoire manque.
            if (item.getSubmissionDateKey() == null || item.getJobOfferKey() == null) {
                continue;
            }

            jdbcTemplate.update("""
                INSERT INTO public.fact_job_application (
                    submission_date_key,
                    job_offer_key,
                    candidate_user_key,
                    application_status,
                    applications_count,
                    is_hired_flag
                )
                SELECT ?, ?, ?, ?, ?, ?
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM public.fact_job_application
                    WHERE submission_date_key = ?
                      AND job_offer_key = ?
                      AND COALESCE(candidate_user_key, -1) = COALESCE(?, -1)
                )
            """,
                    item.getSubmissionDateKey(),
                    item.getJobOfferKey(),
                    item.getCandidateUserKey(),
                    item.getApplicationStatus(),
                    item.getApplicationsCount(),
                    item.getIsHiredFlag(),

                    item.getSubmissionDateKey(),
                    item.getJobOfferKey(),
                    item.getCandidateUserKey()
            );
        }
    }

    private Integer getDateKey(LocalDate date) {
        if (date == null) {
            return null;
        }

        if (dateKeyCache.containsKey(date)) {
            return dateKeyCache.get(date);
        }

        Integer dateKey = jdbcTemplate.query("""
            SELECT date_key
            FROM public.dim_date
            WHERE full_date = ?
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("date_key") : null, Date.valueOf(date));

        if (dateKey != null) {
            dateKeyCache.put(date, dateKey);
        }

        return dateKey;
    }

    private Integer getJobOfferKey(Long jobOfferId) {
        if (jobOfferId == null) {
            return null;
        }

        if (jobOfferKeyCache.containsKey(jobOfferId)) {
            return jobOfferKeyCache.get(jobOfferId);
        }

        Integer jobOfferKey = jdbcTemplate.query("""
            SELECT job_offer_key
            FROM public.dim_job_offer
            WHERE job_offer_id = ?
              AND is_current = TRUE
            ORDER BY job_offer_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("job_offer_key") : null, jobOfferId);

        if (jobOfferKey != null) {
            jobOfferKeyCache.put(jobOfferId, jobOfferKey);
        }

        return jobOfferKey;
    }

    private Integer getUserKey(Long userId) {
        if (userId == null) {
            return null;
        }

        if (userKeyCache.containsKey(userId)) {
            return userKeyCache.get(userId);
        }

        Integer userKey = jdbcTemplate.query("""
            SELECT user_key
            FROM public.dim_user
            WHERE user_id = ?
              AND is_current = TRUE
            ORDER BY user_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("user_key") : null, userId);

        if (userKey != null) {
            userKeyCache.put(userId, userKey);
        }

        return userKey;
    }
}