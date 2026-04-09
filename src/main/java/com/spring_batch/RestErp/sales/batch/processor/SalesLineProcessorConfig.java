package com.spring_batch.RestErp.sales.batch.processor;

import com.spring_batch.RestErp.sales.dto.fact.FactSalesLine;
import com.spring_batch.RestErp.sales.dto.source.FactSalesLineSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SalesLineProcessorConfig {

    @Bean
    public ItemProcessor<FactSalesLineSource, FactSalesLine> salesLineProcessor() {
        return source -> {

            if (source == null || source.getSalesLineId() == null) {
                return null;
            }

            FactSalesLine target = new FactSalesLine();

            target.setSalesLineId(source.getSalesLineId());

            target.setCompanyId(source.getCompanyId());
            target.setCompanyKey(null);

            target.setDateKey(null);

            target.setProductId(source.getProductId());
            target.setProductKey(null);

            target.setQuotationId(source.getQuotationId());
            target.setInvoiceId(source.getInvoiceId());
            target.setSellOrderId(source.getSellOrderId());

            target.setQuantity(source.getQuantity());
            target.setUnitPrice(source.getUnitPrice());
            target.setDiscount(source.getDiscount());
            target.setSubtotal(source.getSubtotal());
            target.setTax(source.getTax());

            // lineRevenue : on prend subtotal si disponible, sinon calcul
            if (source.getSubtotal() != null) {
                target.setLineRevenue(source.getSubtotal());
            } else if (source.getQuantity() != null && source.getUnitPrice() != null) {
                double discount = source.getDiscount() != null ? source.getDiscount() : 0.0;
                target.setLineRevenue((source.getQuantity() * source.getUnitPrice()) - discount);
            } else {
                target.setLineRevenue(null);
            }

            if (source.getTimestamp() != null) {
                target.setSalesDate(source.getTimestamp().toLocalDate());
            } else {
                target.setSalesDate(null);
            }

            return target;
        };
    }
}