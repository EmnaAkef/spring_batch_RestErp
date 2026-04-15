package com.spring_batch.RestErp.finance.batch.processor;

import com.spring_batch.RestErp.finance.dto.fact.FactInvoice;
import com.spring_batch.RestErp.finance.dto.source.FactInvoiceSource;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FactInvoiceProcessor implements ItemProcessor<FactInvoiceSource, FactInvoice> {

    @Override
    public FactInvoice process(FactInvoiceSource source) {

        if (source == null || source.getIssueDate() == null) {
            return null;
        }

        FactInvoice fact = new FactInvoice();

        fact.setInvoiceId(source.getInvoiceId());

        fact.setProjectId(source.getProjectId());
        fact.setInvoiceNumber(source.getInvoiceNumber());
        fact.setInvoiceType(source.getInvoiceType());
        fact.setPaymentType(source.getPaymentType());

        fact.setTotal(toBigDecimal(source.getTotal()));
        fact.setUntaxedAmount(toBigDecimal(source.getUntaxedAmount()));
        fact.setTax(toBigDecimal(source.getTax()));
        fact.setPartialPaidAmount(toBigDecimal(source.getPartiallyPaidAmount()));

        fact.setInvoiceCount(1);

        // Champs techniques ETL pour le writer
        fact.setCompanyId(source.getCompanyId());
        fact.setCustomerId(source.getCustomerId());
        fact.setAgentId(source.getAgentId());
        fact.setStatus(source.getStatus());
        fact.setIssueDate(source.getIssueDate());
        fact.setDueDate(source.getDueDate());

        return fact;
    }

    private BigDecimal toBigDecimal(Double value) {
        if (value == null) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }
}