package com.project.flight.mapper;

import com.project.flight.dto.BillingPaymentDTO;
import com.project.flight.model.BillingPayment;

public class BillingPaymentMapper {

    public static BillingPaymentDTO toDTO(BillingPayment billingPayment) {
        BillingPaymentDTO dto = new BillingPaymentDTO();
        dto.setId(billingPayment.getId());
        dto.setFare(billingPayment.getFare());
        dto.setTax(billingPayment.getTax());
        dto.setServiceFee(billingPayment.getServiceFee());
        dto.setTotalPrice(billingPayment.getTotalPrice());
        return dto;
    }
}