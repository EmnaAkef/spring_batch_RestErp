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

    public FactJobApplicationItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends FactJobApplication> chunk) {
        for (FactJobApplication item : chunk) {

            Integer submissionDateKey = getDateKey(
                    item.getSubmissionDate() != null ? item.getSubmissionDate().toLocalDate() : null
            );

            Integer jobOfferKey = getJobOfferKey(item.getJobOfferId());

            item.setSubmissionDateKey(submissionDateKey);
            item.setJobOfferKey(jobOfferKey);

            // sécurité : ignorer si les clés obligatoires manquent
            if (item.getSubmissionDateKey() == null || item.getJobOfferKey() == null) {
                continue;
            }

            jdbcTemplate.update("""
                INSERT INTO public.fact_job_application (
                    submission_date_key,
                    job_offer_key,
                    application_status,
                    applications_count,
                    is_hired_flag
                )
                VALUES (?, ?, ?, ?, ?)
                ON CONFLICT (submission_date_key, job_offer_key, application_status)
                DO UPDATE SET
                    applications_count = EXCLUDED.applications_count,
                    is_hired_flag = EXCLUDED.is_hired_flag
            """,
                    item.getSubmissionDateKey(),
                    item.getJobOfferKey(),
                    item.getApplicationStatus(),
                    item.getApplicationsCount(),
                    item.getIsHiredFlag()
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
}