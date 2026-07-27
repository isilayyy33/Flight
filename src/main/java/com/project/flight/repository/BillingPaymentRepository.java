package com.project.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.flight.model.BillingPayment;

public interface BillingPaymentRepository extends JpaRepository<BillingPayment, Long> {
}