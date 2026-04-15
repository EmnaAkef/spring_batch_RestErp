package com.spring_batch.RestErp.sales.batch.processor;


import com.spring_batch.RestErp.sales.dto.dim.DimProduct;
import com.spring_batch.RestErp.sales.dto.source.ProductSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class ProductProcessorConfig {

    @Bean
    public ItemProcessor<ProductSource, DimProduct> productProcessor() {
        return source -> {

            if (source == null || source.getProductId() == null) {
                return null;
            }

            String cleanedProductName = cleanText(source.getProductName());
            if (cleanedProductName == null) {
                return null;
            }

            DimProduct target = new DimProduct();

            target.setProductId(source.getProductId());
            target.setCompanyId(source.getCompanyId());
            target.setCompanyKey(null);

            target.setProductName(cleanedProductName);
            target.setCategory(cleanText(source.getCategory()));

            target.setProductType(mapProductType(source.getProductType()));
            target.setStatus(mapStatus(source.getStatus()));
            target.setUom(mapUom(source.getUnit()));

            target.setArchive(source.getArchive());

            target.setEffectiveFrom(LocalDateTime.now());
            target.setEffectiveTo(null);
            target.setIsCurrent(true);

            return target;
        };
    }

    /**
     * Nettoyage des champs texte :
     * - trim
     * - suppression des espaces multiples
     * - null si vide
     */
    private String cleanText(String value) {
        if (value == null) {
            return null;
        }

        String cleaned = value.trim().replaceAll("\\s+", " ");
        return cleaned.isEmpty() ? null : cleaned;
    }

    /**
     * Mapping de product_type.
     * Adapte les libellés si tu connais le vrai sens métier.
     */
    private String mapProductType(Integer productType) {
        if (productType == null) {
            return null;
        }

        return switch (productType) {
            case 0 -> "CONSUMER_GOODS";
            case 1 -> "INDUSTRIAL_GOODS";
            case 2 -> "SERVICES";
            case 3 -> "LUXURY_GOODS";
            case 4 -> "CONVENIENCE_GOODS";
            case 5 -> "SPECIALTY_GOODS";
            case 6 -> "DIGITAL_PRODUCTS";
            case 7 -> "SEASONAL_PRODUCTS";
            case 8 -> "ROW_MATERIAL";
            case 9 -> "INTERMEDIATE_MATERIAL";
            default -> "OTHER";

        };
    }

    /**
     * Mapping de status.
     * À ajuster si tu connais le vrai dictionnaire métier.
     */
    private String mapStatus(Integer status) {
        if (status == null) {
            return null;
        }

        return switch (status) {
            case 0 -> "WAITING_PROOF_CONFIRMATION";
            case 1 -> "IN_WAREHOUSE";
            default -> "UNKNOWN";
        };
    }

    /**
     * Mapping de unit -> uom.
     * À ajuster selon le vrai enum métier de la source.
     */
    private String mapUom(Integer unit) {
        if (unit == null) {
            return null;
        }

        return switch (unit) {
            case 0 -> "PIECE";
            case 1 -> "BOX";
            case 2 -> "KG";
            case 3 -> "LITER";
            case 4 -> "PACK";
            case 5 -> "UNIT";
            default -> "UNKNOWN";
        };
    }
}
