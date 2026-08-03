package com.project.flight.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillingPaymentDTO {

    private Long id;
    private BigDecimal fare;
    private BigDecimal tax;
    private BigDecimal serviceFee;
    private BigDecimal totalPrice;
}