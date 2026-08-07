package com.project.flight.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.flight.dto.BillingPaymentDTO;
import com.project.flight.service.BillingPaymentService;

@RestController
@RequestMapping("/api/billing-payments")
public class BillingPaymentController {

    private final BillingPaymentService billingPaymentService;

    public BillingPaymentController(BillingPaymentService billingPaymentService) {
        this.billingPaymentService = billingPaymentService;
    }

    // POST /api/billing-payments
    @PostMapping
    public ResponseEntity<BillingPaymentDTO> createBillingPayment(@RequestBody BillingPaymentDTO dto) {
        BillingPaymentDTO saved = billingPaymentService.saveBillingPayment(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // GET /api/billing-payments
    @GetMapping
    public ResponseEntity<List<BillingPaymentDTO>> getAllBillingPayments() {
        return ResponseEntity.ok(billingPaymentService.getAllBillingPayments());
    }

    // GET /api/billing-payments/{id}
    @GetMapping("/{id}")
    public ResponseEntity<BillingPaymentDTO> getBillingPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(billingPaymentService.getBillingPaymentById(id));
    }

    // PUT /api/billing-payments/{id}
    @PutMapping("/{id}")
    public ResponseEntity<BillingPaymentDTO> updateBillingPayment(@PathVariable Long id, @RequestBody BillingPaymentDTO dto) {
        return ResponseEntity.ok(billingPaymentService.updateBillingPayment(id, dto));
    }

    // DELETE /api/billing-payments/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBillingPayment(@PathVariable Long id) {
        billingPaymentService.deleteBillingPayment(id);
        return ResponseEntity.noContent().build();
    }
}