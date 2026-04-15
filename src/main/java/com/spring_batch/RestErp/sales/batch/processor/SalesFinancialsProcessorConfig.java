package com.spring_batch.RestErp.sales.batch.processor;

import com.spring_batch.RestErp.sales.dto.fact.FactSalesFinancials;
import com.spring_batch.RestErp.sales.dto.source.FactSalesFinancialsSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SalesFinancialsProcessorConfig {

    @Bean
    public ItemProcessor<FactSalesFinancialsSource, FactSalesFinancials> salesFinancialsProcessor() {
        return source -> {

            if (source == null || source.getInvoiceId() == null) {
                return null;
            }

            FactSalesFinancials target = new FactSalesFinancials();

            target.setCompanyId(source.getCompanyId());
            target.setCompanyKey(null);

            if (source.getInvoiceDate() != null) {
                target.setSalesDate(source.getInvoiceDate().toLocalDate());
            } else {
                target.setSalesDate(null);
            }
            target.setDateKey(null);

            target.setCustomerId(source.getCustomerId());
            target.setCustomerKey(null);

            target.setAgentId(source.getAgentId());
            target.setAgentUserKey(null);

            target.setInvoiceId(source.getInvoiceId());
            target.setQuotationId(source.getQuotationId());

            target.setInvoiceTotal(source.getInvoiceTotal());
            target.setInvoiceUntaxedAmount(source.getInvoiceUntaxedAmount());

            target.setInvoiceCount(1);
            target.setQuotationCount(source.getQuotationCount() != null ? source.getQuotationCount() : 0);
            target.setReceiptCount(source.getReceiptCount() != null ? source.getReceiptCount() : 0);

            target.setQuotationTotal(source.getQuotationTotal());
            target.setAllocatedAmount(source.getAllocatedAmount());
            target.setUnallocatedAmount(source.getUnallocatedAmount());

            target.setPaymentState(cleanText(source.getPaymentState()));
            target.setInvoiceStatus(cleanText(mapInvoiceStatus(source.getInvoiceStatus())));
            target.setQuotationState(cleanText(mapQuotationStatus(source.getQuotationState())));

            return target;
        };
    }

    private String cleanText(String value) {
        if (value == null) {
            return null;
        }

        String cleaned = value.trim().replaceAll("\\s+", " ");
        return cleaned.isEmpty() ? null : cleaned;
    }

    private String mapInvoiceStatus(Integer invoiceStatus) {
        if (invoiceStatus == null) {
            return null;
        }

        return switch (invoiceStatus) {
            case 0 -> "PAID";
            case 1 -> "UNPAID";
            case 2 -> "WAITING";
            case 3 -> "REFUNDED";
            case 4 -> "ACCEPTED";
            case 5 -> "PARTIALLY PAID";
            case 6 -> "REJECTED";
            case 7 -> "CREDIT MEMO";
            default -> "UNKNOWN";
        };
    }

    private String mapQuotationStatus(String quotationStatus) {
        if (quotationStatus == null) {
            return null;
        }

        Integer value;
        try {
            value = Integer.parseInt(quotationStatus);
        } catch (NumberFormatException e) {
            return quotationStatus;
        }

        return switch (value) {
            case 0 -> "DRAFT";
            case 1 -> "SENT";
            case 2 -> "ACCEPTED";
            case 3 -> "REJECTED";
            case 4 -> "EXPIRED";
            default -> "UNKNOWN";
        };
    }
}