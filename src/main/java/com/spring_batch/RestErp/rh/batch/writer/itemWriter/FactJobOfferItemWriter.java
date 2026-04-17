package com.spring_batch.RestErp.rh.batch.writer.item;

import com.spring_batch.RestErp.rh.dto.fact.FactJobOffer;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class FactJobOfferItemWriter implements ItemWriter<FactJobOffer> {

    private final JdbcTemplate jdbcTemplate;

    private final Map<LocalDate, Integer> dateKeyCache = new HashMap<>();
    private final Map<Long, Integer> jobOfferKeyCache = new HashMap<>();
    private final Map<Long, Integer> userKeyCache = new HashMap<>();

    public FactJobOfferItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends FactJobOffer> chunk) throws Exception {
        for (FactJobOffer item : chunk) {

            Integer postingDateKey = getDateKey(
                    item.getPostingDate() != null ? item.getPostingDate().toLocalDate() : null
            );
            if (postingDateKey == null) {
                throw new IllegalStateException(
                        "posting_date_key introuvable pour postingDate = " + item.getPostingDate()
                );
            }

            Integer jobOfferKey = getJobOfferKey(item.getJobOfferId());
            if (jobOfferKey == null) {
                throw new IllegalStateException(
                        "job_offer_key introuvable pour job_offer_id = " + item.getJobOfferId()
                );
            }

            Integer createdByUserKey = getUserKey(item.getSubmittedUserId());

            item.setPostingDateKey(postingDateKey);
            item.setJobOfferKey(jobOfferKey);
            item.setCreatedByUserKey(createdByUserKey);

            jdbcTemplate.update("""
                INSERT INTO public.fact_job_offer (
                    posting_date_key,
                    job_offer_key,
                    created_by_user_key,
                    status,
                    job_offers_count,
                    is_active_flag
                )
                SELECT ?, ?, ?, ?, ?, ?
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM public.fact_job_offer
                    WHERE posting_date_key = ?
                      AND job_offer_key = ?
                      AND COALESCE(created_by_user_key, -1) = COALESCE(?, -1)
                )
            """,
                    item.getPostingDateKey(),
                    item.getJobOfferKey(),
                    item.getCreatedByUserKey(),
                    item.getStatus(),
                    item.getJobOffersCount(),
                    item.getIsActiveFlag(),

                    item.getPostingDateKey(),
                    item.getJobOfferKey(),
                    item.getCreatedByUserKey()
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