package com.spring_batch.RestErp.sales.batch.processor;

import com.spring_batch.RestErp.sales.dto.fact.FactSalesOrder;
import com.spring_batch.RestErp.sales.dto.source.FactSalesOrderSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SalesOrderProcessorConfig {

    @Bean
    public ItemProcessor<FactSalesOrderSource, FactSalesOrder> salesOrderProcessor() {
        return source -> {

            if (source == null || source.getSellOrderId() == null) {
                return null;
            }

            FactSalesOrder target = new FactSalesOrder();

            target.setSellOrderId(source.getSellOrderId());

            target.setCompanyId(source.getCompanyId());
            target.setCompanyKey(null);

            if (source.getOrderDate() != null) {
                target.setOrderDate(source.getOrderDate().toLocalDate());
            } else {
                target.setOrderDate(null);
            }
            target.setDateKey(null);

            target.setCustomerId(source.getCustomerId());
            target.setCustomerKey(null);

            target.setQuotationId(source.getQuotationId());
            target.setInvoiceId(source.getInvoiceId());

            target.setSoCount(1);
            target.setStatus(mapSalesOrderStatus(source.getStatus()));

            return target;
        };
    }

    private String mapSalesOrderStatus(Integer status) {
        if (status == null) {
            return null;
        }

        return switch (status) {
            case 0 -> "OPEN";
            case 1 -> "CLOSE";
            default -> "UNKNOWN";
        };
    }
}