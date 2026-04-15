package com.spring_batch.RestErp.finance.batch.processor;

import com.spring_batch.RestErp.finance.dto.fact.FactAsset;
import com.spring_batch.RestErp.finance.dto.source.FactAssetSource;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FactAssetProcessor implements ItemProcessor<FactAssetSource, FactAsset> {

    @Override
    public FactAsset process(FactAssetSource source) {

        if (source == null || source.getAssignDate() == null) {
            return null;
        }

        FactAsset fact = new FactAsset();

        BigDecimal assetValue = toBigDecimal(source.getAssetValue());
        BigDecimal depreciationAmount = toBigDecimal(source.getDepreciationAmount());
        BigDecimal percentage = toBigDecimal(source.getPercentage());

        fact.setAssetId(source.getAssetId());

        fact.setAssetValue(assetValue);
        fact.setDepreciationAmount(depreciationAmount);
        fact.setNetBookValue(assetValue.subtract(depreciationAmount));
        fact.setPercentage(percentage);

        fact.setYearsDuration(source.getYearsDuration() != null ? source.getYearsDuration() : 0);
        fact.setAssetCount(1);

        fact.setCompanyId(source.getCompanyId());
        fact.setAssetType(mapAssetType(source.getAssetType()));
        fact.setAssignDate(source.getAssignDate());

        return fact;
    }

    private BigDecimal toBigDecimal(Double value) {
        if (value == null) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }

    private String mapAssetType(String rawType) {
        if (rawType == null || rawType.isBlank()) {
            return null;
        }

        String value = rawType.trim();

        return switch (value) {
            case "0" -> "Furniture";
            case "1" -> "Infrastructure";
            case "2" -> "IT Equipment";
            case "3" -> "Mobile Devices";
            case "4" -> "Office Equipment";
            case "5" -> "Security Systems";
            case "6" -> "Vehicles";

            case "Furniture" -> "Furniture";
            case "Infrastructure" -> "Infrastructure";
            case "IT Equipment" -> "IT Equipment";
            case "Mobile Devices" -> "Mobile Devices";
            case "Office Equipment" -> "Office Equipment";
            case "Security Systems" -> "Security Systems";
            case "Vehicles" -> "Vehicles";

            default -> null;
        };
    }
}