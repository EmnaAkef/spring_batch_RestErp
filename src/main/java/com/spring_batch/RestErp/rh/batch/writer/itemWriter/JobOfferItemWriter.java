package com.spring_batch.RestErp.rh.batch.writer.itemWriter;

import com.spring_batch.RestErp.rh.dto.dim.DimJobOffer;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JobOfferItemWriter implements ItemWriter<DimJobOffer> {

    private final JdbcTemplate jdbcTemplate;
    private final Map<Long, Integer> userKeyCache = new HashMap<>();

    public JobOfferItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends DimJobOffer> chunk) {
        for (DimJobOffer item : chunk) {

            DimJobOffer existing = findCurrentByJobOfferId(item.getJobOfferId());

            if (existing == null) {
                insertJobOffer(item);
                continue;
            }

            if (isSame(existing, item)) {
                continue;
            }

            closeCurrentVersion(existing.getJobOfferKey());
            insertJobOffer(item);
        }
    }

    private DimJobOffer findCurrentByJobOfferId(Long jobOfferId) {
        String sql = """
            SELECT
                job_offer_key,
                job_offer_id,
                job_title,
                employment_type,
                location,
                posting_date,
                expiry_date,
                required_experience_years,
                salary_range_min,
                salary_range_max,
                status,
                submitted_user_key,
                effective_from,
                effective_to,
                is_current
            FROM dim_job_offer
            WHERE job_offer_id = ?
              AND is_current = true
            """;

        return jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                DimJobOffer dim = new DimJobOffer();
                dim.setJobOfferKey(rs.getInt("job_offer_key"));
                dim.setJobOfferId(rs.getLong("job_offer_id"));
                dim.setJobTitle(rs.getString("job_title"));
                dim.setEmploymentType(rs.getString("employment_type"));
                dim.setLocation(rs.getString("location"));
                dim.setPostingDate(rs.getTimestamp("posting_date"));
                dim.setExpiryDate(rs.getTimestamp("expiry_date"));
                dim.setRequiredExperienceYears((Integer) rs.getObject("required_experience_years"));
                dim.setSalaryRangeMin(rs.getBigDecimal("salary_range_min"));
                dim.setSalaryRangeMax(rs.getBigDecimal("salary_range_max"));
                dim.setStatus(rs.getString("status"));

                // Ici on compare la valeur DW déjà stockée
                Long submittedUserKey = rs.getObject("submitted_user_key") != null
                        ? rs.getLong("submitted_user_key")
                        : null;
                dim.setSubmittedUserId(submittedUserKey);

                dim.setEffectiveFrom(rs.getTimestamp("effective_from"));
                dim.setEffectiveTo(rs.getTimestamp("effective_to"));
                dim.setIsCurrent(rs.getBoolean("is_current"));
                return dim;
            }
            return null;
        }, jobOfferId);
    }

    private boolean isSame(DimJobOffer existing, DimJobOffer incoming) {
        Integer incomingSubmittedUserKey = getUserKey(incoming.getSubmittedUserId());

        return Objects.equals(existing.getJobTitle(), incoming.getJobTitle())
                && Objects.equals(existing.getEmploymentType(), incoming.getEmploymentType())
                && Objects.equals(existing.getLocation(), incoming.getLocation())
                && Objects.equals(existing.getPostingDate(), incoming.getPostingDate())
                && Objects.equals(existing.getExpiryDate(), incoming.getExpiryDate())
                && Objects.equals(existing.getRequiredExperienceYears(), incoming.getRequiredExperienceYears())
                && Objects.equals(existing.getSalaryRangeMin(), incoming.getSalaryRangeMin())
                && Objects.equals(existing.getSalaryRangeMax(), incoming.getSalaryRangeMax())
                && Objects.equals(existing.getStatus(), incoming.getStatus())
                && Objects.equals(existing.getSubmittedUserId(), incomingSubmittedUserKey != null ? incomingSubmittedUserKey.longValue() : null);
    }

    private void closeCurrentVersion(Integer jobOfferKey) {
        String sql = """
            UPDATE dim_job_offer
            SET effective_to = ?, is_current = false
            WHERE job_offer_key = ?
            """;

        jdbcTemplate.update(sql, Timestamp.from(Instant.now()), jobOfferKey);
    }

    private void insertJobOffer(DimJobOffer item) {
        String sql = """
            INSERT INTO dim_job_offer (
                job_offer_id,
                job_title,
                employment_type,
                location,
                posting_date,
                expiry_date,
                required_experience_years,
                salary_range_min,
                salary_range_max,
                status,
                submitted_user_key,
                effective_from,
                effective_to,
                is_current
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        Integer submittedUserKey = getUserKey(item.getSubmittedUserId());

        jdbcTemplate.update(sql,
                item.getJobOfferId(),
                item.getJobTitle(),
                item.getEmploymentType(),
                item.getLocation(),
                item.getPostingDate(),
                item.getExpiryDate(),
                item.getRequiredExperienceYears(),
                item.getSalaryRangeMin(),
                item.getSalaryRangeMax(),
                item.getStatus(),
                submittedUserKey,
                item.getEffectiveFrom(),
                item.getEffectiveTo(),
                item.getIsCurrent());
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
            FROM dim_user
            WHERE user_id = ?
              AND is_current = true
            ORDER BY user_key DESC
            LIMIT 1
            """, rs -> rs.next() ? rs.getInt("user_key") : null, userId);

        if (userKey != null) {
            userKeyCache.put(userId, userKey);
        }

        return userKey;
    }
}