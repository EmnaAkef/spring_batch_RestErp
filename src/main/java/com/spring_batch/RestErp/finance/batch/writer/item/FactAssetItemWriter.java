package com.spring_batch.RestErp.finance.batch.writer.item;

import com.spring_batch.RestErp.finance.dto.fact.FactAsset;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class FactAssetItemWriter implements ItemWriter<FactAsset> {

    private final JdbcTemplate jdbcTemplate;

    private final Map<Long, Integer> companyKeyCache = new HashMap<>();
    private final Map<String, Integer> assetTypeKeyCache = new HashMap<>();
    private final Map<LocalDate, Integer> dateKeyCache = new HashMap<>();

    public FactAssetItemWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(Chunk<? extends FactAsset> chunk) {
        for (FactAsset item : chunk) {

            Integer companyKey = getCompanyKey(item.getCompanyId());
            Integer dateKey = getDateKey(item.getAssignDate() != null ? item.getAssignDate().toLocalDate() : null);
            Integer assetTypeKey = getAssetTypeKey(item.getAssetType());

            item.setCompanyKey(companyKey);
            item.setDateKey(dateKey);
            item.setAssetTypeKey(assetTypeKey);

            if (item.getAssetId() == null || item.getCompanyKey() == null || item.getDateKey() == null || item.getAssetTypeKey() == null) {
                continue;
            }

            jdbcTemplate.update("""
                INSERT INTO public.fact_asset (
                    asset_id,
                    date_key,
                    company_key,
                    asset_type_key,
                    asset_value,
                    depreciation_amount,
                    net_book_value,
                    percentage,
                    years_duration,
                    asset_count
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (asset_id, company_key)
                DO UPDATE SET
                    date_key = EXCLUDED.date_key,
                    asset_type_key = EXCLUDED.asset_type_key,
                    asset_value = EXCLUDED.asset_value,
                    depreciation_amount = EXCLUDED.depreciation_amount,
                    net_book_value = EXCLUDED.net_book_value,
                    percentage = EXCLUDED.percentage,
                    years_duration = EXCLUDED.years_duration,
                    asset_count = EXCLUDED.asset_count
            """,
                    item.getAssetId(),
                    item.getDateKey(),
                    item.getCompanyKey(),
                    item.getAssetTypeKey(),
                    item.getAssetValue(),
                    item.getDepreciationAmount(),
                    item.getNetBookValue(),
                    item.getPercentage(),
                    item.getYearsDuration(),
                    item.getAssetCount()
            );
        }
    }

    private Integer getCompanyKey(Long companyId) {
        if (companyId == null) return null;
        if (companyKeyCache.containsKey(companyId)) return companyKeyCache.get(companyId);

        Integer companyKey = jdbcTemplate.query("""
            SELECT company_key
            FROM public.dim_company
            WHERE company_id = ?
              AND is_current = TRUE
            ORDER BY company_key DESC
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("company_key") : null, companyId);

        if (companyKey != null) companyKeyCache.put(companyId, companyKey);
        return companyKey;
    }

    private Integer getDateKey(LocalDate date) {
        if (date == null) return null;
        if (dateKeyCache.containsKey(date)) return dateKeyCache.get(date);

        Integer dateKey = jdbcTemplate.query("""
            SELECT date_key
            FROM public.dim_date
            WHERE full_date = ?
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("date_key") : null, Date.valueOf(date));

        if (dateKey != null) dateKeyCache.put(date, dateKey);
        return dateKey;
    }

    private Integer getAssetTypeKey(String assetType) {
        if (assetType == null || assetType.isBlank()) return null;

        String normalized = assetType.trim();
        if (assetTypeKeyCache.containsKey(normalized)) return assetTypeKeyCache.get(normalized);

        Integer assetTypeKey = jdbcTemplate.query("""
            SELECT asset_type_key
            FROM public.dim_asset_type
            WHERE asset_type = ?
            LIMIT 1
        """, rs -> rs.next() ? rs.getInt("asset_type_key") : null, normalized);

        if (assetTypeKey != null) assetTypeKeyCache.put(normalized, assetTypeKey);
        return assetTypeKey;
    }
}