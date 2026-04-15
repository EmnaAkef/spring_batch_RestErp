package com.spring_batch.RestErp.sales.batch.writer.itemWriter;

import com.spring_batch.RestErp.sales.dto.fact.FactDeal;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DealItemWriter implements ItemWriter<FactDeal> {

    private final JdbcTemplate jdbcTemplate;

    private final Map<Long, Integer> companyKeyCache = new HashMap<>();
    private final Map<LocalDate, Integer> dateKeyCache = new HashMap<>();
    private final Map<String, Integer> customerKeyCache = new HashMap<>();
    private final Map<String, Integer> ownerUserKeyCache = new HashMap<>();
    private final Map<String, Integer> workstatusKeyCache = new HashMap<>();

    public DealItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends FactDeal> chunk) throws Exception {
        for (FactDeal item : chunk) {

            Integer companyKey = getCompanyKey(item.getCompanyId());
            if (companyKey == null) {
                throw new IllegalStateException(
                        "Aucune company trouvée dans dim_company pour company_id = " + item.getCompanyId()
                );
            }
            item.setCompanyKey(companyKey);

            Integer closeDateKey = getDateKey(item.getCloseDate());
            item.setCloseDateKey(closeDateKey);

            Integer customerKey = getCustomerKey(item.getCompanyId(), item.getCustomerId());
            item.setCustomerKey(customerKey);

            Integer ownerUserKey = getOwnerUserKey(item.getCompanyId(), item.getOwnerUserId());
            item.setOwnerUserKey(ownerUserKey);

            Integer workstatusKey = getWorkstatusKey(item.getCompanyId(), item.getWorkstatusId());
            item.setWorkstatusKey(workstatusKey);

            jdbcTemplate.update("""
                INSERT INTO fact_deal (
                    deal_id,
                    company_id,
                    close_date_key,
                    company_key,
                    customer_key,
                    owner_user_key,
                    workstatus_key,
                    deal_value,
                    deal_count,
                    is_closed,
                    is_archived
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """,
                    item.getDealId(),
                    item.getCompanyId(),
                    item.getCloseDateKey(),
                    item.getCompanyKey(),
                    item.getCustomerKey(),
                    item.getOwnerUserKey(),
                    item.getWorkstatusKey(),
                    item.getDealValue(),
                    item.getDealCount(),
                    item.getIsClosed(),
                    item.getIsArchived()
            );
        }
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

    private Integer getDateKey(LocalDate closeDate) {
        if (closeDate == null) {
            return null;
        }

        if (dateKeyCache.containsKey(closeDate)) {
            return dateKeyCache.get(closeDate);
        }

        Integer dateKey = jdbcTemplate.query("""
            SELECT date_key
            FROM dim_date
            WHERE full_date = ?
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("date_key") : null, closeDate);

        if (dateKey != null) {
            dateKeyCache.put(closeDate, dateKey);
        }

        return dateKey;
    }

    private Integer getCustomerKey(Long companyId, Long customerId) {
        if (companyId == null || customerId == null) {
            return null;
        }

        String cacheKey = companyId + "|" + customerId;

        if (customerKeyCache.containsKey(cacheKey)) {
            return customerKeyCache.get(cacheKey);
        }

        Integer customerKey = jdbcTemplate.query("""
            SELECT customer_key
            FROM dim_customer
            WHERE company_id = ?
              AND customer_id = ?
              AND is_current = TRUE
            ORDER BY customer_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("customer_key") : null, companyId, customerId);

        if (customerKey != null) {
            customerKeyCache.put(cacheKey, customerKey);
        }

        return customerKey;
    }

    private Integer getOwnerUserKey(Long companyId, Long ownerUserId) {
        if (companyId == null || ownerUserId == null) {
            return null;
        }

        String cacheKey = companyId + "|" + ownerUserId;

        if (ownerUserKeyCache.containsKey(cacheKey)) {
            return ownerUserKeyCache.get(cacheKey);
        }

        Integer ownerUserKey = jdbcTemplate.query("""
            SELECT user_key
            FROM dim_user
            WHERE company_id = ?
              AND user_id = ?
              AND is_current = TRUE
            ORDER BY user_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("user_key") : null, companyId, ownerUserId);

        if (ownerUserKey != null) {
            ownerUserKeyCache.put(cacheKey, ownerUserKey);
        }

        return ownerUserKey;
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