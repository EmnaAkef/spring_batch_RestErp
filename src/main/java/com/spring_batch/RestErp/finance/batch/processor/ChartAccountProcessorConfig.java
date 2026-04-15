package com.spring_batch.RestErp.finance.batch.processor;

import com.spring_batch.RestErp.finance.dto.dim.DimChartAccount;
import com.spring_batch.RestErp.finance.dto.source.ChartAccountSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Locale;

@Configuration
public class ChartAccountProcessorConfig {

    @Bean
    public ItemProcessor<ChartAccountSource, DimChartAccount> chartAccountProcessor() {
        return source -> {

            if (source == null || source.getChartId() == null) {
                return null;
            }

            String cleanedAccountName = cleanText(source.getAccountName());
            if (cleanedAccountName == null) {
                return null;
            }

            DimChartAccount target = new DimChartAccount();

            target.setChartId(source.getChartId());
            target.setCompanyId(source.getCompanyId());
            target.setCompanyKey(null);

            target.setAccountName(cleanedAccountName);
            target.setTransactionType(mapTransactionType(source.getTransactionType()));
            target.setAccountType(normalizeAccountType(source.getAccountType()));

            target.setEffectiveFrom(LocalDateTime.now());
            target.setEffectiveTo(null);
            target.setIsCurrent(true);

            return target;
        };
    }

    /**
     * Nettoyage texte :
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
     * Transaction type :
     * on garde un placeholder technique pour le moment,
     * le vrai mapping sera fixé plus tard.
     */
    private String mapTransactionType(Integer transactionType) {
        if (transactionType == null) {
            return null;
        }

        return switch (transactionType) {//not adjusted yet
            case 0 -> "CLIENT";
            case 1 -> "COMPANY";
            case 2 -> "CUSTOMER";
            case 3 -> " INDIVIDUAL";
            case 4 -> "INSTITUTION";
            case 5 -> "GOVERNMENT";
            default -> "UNKNOWN";
        };
    }


    private String normalizeAccountType(String accountType) {
        if (accountType == null) {
            return null;
        }

        return String.valueOf(accountType).toLowerCase(Locale.ROOT);
    }
}
