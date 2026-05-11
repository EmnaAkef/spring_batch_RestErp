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
    private final Map<String, Integer> jobOfferKeyCache = new HashMap<>();
    private final Map<String, Integer> submittedUserKeyCache = new HashMap<>();
    private final Map<Long, Integer> companyKeyCache = new HashMap<>();

    public FactJobApplicationItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends FactJobApplication> chunk) {
        for (FactJobApplication item : chunk) {

            Integer submissionDateKey = getDateKey(
                    item.getSubmissionDate() != null ? item.getSubmissionDate().toLocalDate() : null
            );

            Integer jobOfferKey = getJobOfferKey(item.getCompanyId(), item.getJobOfferId());
            Integer submittedUserKey = getSubmittedUserKeyFromJobOffer(item.getCompanyId(), item.getJobOfferId());
            Integer companyKey = getCompanyKey(item.getCompanyId());

            if (submissionDateKey == null || jobOfferKey == null || submittedUserKey == null || companyKey == null) {
                System.out.println("SKIP fact_job_application | jobOfferId=" + item.getJobOfferId()
                        + " | companyId=" + item.getCompanyId()
                        + " | dateKey=" + submissionDateKey
                        + " | jobOfferKey=" + jobOfferKey
                        + " | submittedUserKey=" + submittedUserKey
                        + " | companyKey=" + companyKey);
                continue;
            }

            item.setSubmissionDateKey(submissionDateKey);
            item.setJobOfferKey(jobOfferKey);
            item.setSubmittedUserKey(submittedUserKey);
            item.setCompanyKey(companyKey);

            jdbcTemplate.update("""
                INSERT INTO public.fact_job_application (
                  submission_date_key,
                  job_offer_key,
                  submitted_user_key,
                  company_key,
                  application_status,
                  applications_count,
                  is_hired_flag
              )
              VALUES (?, ?, ?, ?, ?, ?, ?)
              ON CONFLICT (
                  submission_date_key,
                  job_offer_key,
                  company_key,
                  application_status
              )
              DO UPDATE SET
                  applications_count = fact_job_application.applications_count + EXCLUDED.applications_count,
                  is_hired_flag = fact_job_application.is_hired_flag + EXCLUDED.is_hired_flag,
                  submitted_user_key = EXCLUDED.submitted_user_key;
            """,
                    item.getSubmissionDateKey(),
                    item.getJobOfferKey(),
                    item.getSubmittedUserKey(),
                    item.getCompanyKey(),
                    item.getApplicationStatus(),
                    item.getApplicationsCount(),
                    item.getIsHiredFlag()
            );
        }
    }

    private Integer getDateKey(LocalDate date) {
        if (date == null) return null;

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

    private Integer getSubmittedUserKeyFromJobOffer(Long companyId, Long jobOfferId) {
        if (companyId == null || jobOfferId == null) return null;

        String cacheKey = companyId + "|" + jobOfferId;

        if (submittedUserKeyCache.containsKey(cacheKey)) {
            return submittedUserKeyCache.get(cacheKey);
        }

        Integer submittedUserKey = jdbcTemplate.query("""
            SELECT submitted_user_key
            FROM public.dim_job_offer
            WHERE company_id = ?
              AND job_offer_id = ?
              AND is_current = TRUE
            ORDER BY job_offer_key DESC
            LIMIT 1
        """, rs -> rs.next() ? (Integer) rs.getObject("submitted_user_key") : null, companyId, jobOfferId);

        if (submittedUserKey != null) {
            submittedUserKeyCache.put(cacheKey, submittedUserKey);
        }

        return submittedUserKey;
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
}