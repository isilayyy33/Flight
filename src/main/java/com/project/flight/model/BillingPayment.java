package com.project.flight.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "billing_payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillingPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "tax")
    private BigDecimal tax;
     /*Money should never be stored as int or double in real systems int can't hold decimals (no cents), and double has rounding errors. BigDecimal is the standard for currency. */
}