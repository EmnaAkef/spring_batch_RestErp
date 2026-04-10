package com.spring_batch.RestErp.finance.batch.processor;

import com.spring_batch.RestErp.finance.dto.fact.FactCashMovement;
import com.spring_batch.RestErp.finance.dto.source.CashMovementSource;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CashMovementProcessor implements ItemProcessor<CashMovementSource, FactCashMovement> {

    @Override
    public FactCashMovement process(CashMovementSource source) {

        if (source == null || source.getTimestamp() == null) {
            return null;
        }

        FactCashMovement fact = new FactCashMovement();

        BigDecimal debit = toBigDecimal(source.getDebit());
        BigDecimal credit = toBigDecimal(source.getCredit());
        BigDecimal debitRec = toBigDecimal(source.getDebitReconciliation());
        BigDecimal creditRec = toBigDecimal(source.getCreditReconciliation());
        BigDecimal opening = toBigDecimal(source.getOpeningBalance());

        fact.setCashId(source.getCashId());

        fact.setDebit(debit);
        fact.setCredit(credit);
        fact.setNetAmount(debit.subtract(credit));

        fact.setOpeningBalance(opening);
        fact.setDebitReconciliation(debitRec);
        fact.setCreditReconciliation(creditRec);
        fact.setReconciliationGap(debitRec.subtract(creditRec));

        fact.setCompanyId(source.getCompanyId());
        fact.setChartId(source.getChartId());
        fact.setUserId(source.getUserId());
        fact.setTimestamp(source.getTimestamp());

        return fact;
    }

    private BigDecimal toBigDecimal(Double value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }
}