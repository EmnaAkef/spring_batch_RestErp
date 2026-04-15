package com.spring_batch.RestErp.finance.batch.processor;

import com.spring_batch.RestErp.finance.dto.fact.FactBill;
import com.spring_batch.RestErp.finance.dto.source.FactBillSource;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FactBillProcessor implements ItemProcessor<FactBillSource, FactBill> {

    @Override
    public FactBill process(FactBillSource source) {

        if (source == null || source.getIssueDate() == null) {
            return null;
        }

        FactBill fact = new FactBill();

        fact.setBillId(source.getBillId());

        fact.setTotal(toBigDecimal(source.getTotal()));
        fact.setUntaxedAmount(toBigDecimal(source.getUntaxedAmount()));
        fact.setTax(toBigDecimal(source.getTax()));

        fact.setBillCount(1);

        // Champs techniques ETL pour le writer
        fact.setCompanyId(source.getCompanyId());
        fact.setVendorId(source.getVendorId());
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