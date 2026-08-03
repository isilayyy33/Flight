package com.project.flight.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.flight.dto.BillingPaymentDTO;
import com.project.flight.exception.NoDataFoundException;
import com.project.flight.mapper.BillingPaymentMapper;
import com.project.flight.model.BillingPayment;
import com.project.flight.repository.BillingPaymentRepository;

@Service
public class BillingPaymentService {

    private final BillingPaymentRepository billingPaymentRepository;

    public BillingPaymentService(BillingPaymentRepository billingPaymentRepository) {
        this.billingPaymentRepository = billingPaymentRepository;
    }

    // CREATE
    public BillingPaymentDTO saveBillingPayment(BillingPaymentDTO dto) {
        // Building a brand-new entity here, so dto.getId() is never used —
        // this means there's no risk of accidentally overwriting an existing record.
        BillingPayment billingPayment = new BillingPayment();
        billingPayment.setFare(dto.getFare());
        billingPayment.setTax(dto.getTax());
        billingPayment.setServiceFee(dto.getServiceFee());
        billingPayment.setTotalPrice(dto.getTotalPrice());
        BillingPayment saved = billingPaymentRepository.save(billingPayment);
        return BillingPaymentMapper.toDTO(saved);
    }

    // READ - get all
    public List<BillingPaymentDTO> getAllBillingPayments() {
        return billingPaymentRepository.findAll()
                .stream()
                .map(BillingPaymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    // READ - get one by id
    public BillingPaymentDTO getBillingPaymentById(Long id) {
        BillingPayment billingPayment = billingPaymentRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException("BillingPayment not found with id: " + id));
        return BillingPaymentMapper.toDTO(billingPayment);
    }

    // UPDATE
    public BillingPaymentDTO updateBillingPayment(Long id, BillingPaymentDTO dto) {
        BillingPayment existing = billingPaymentRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException("BillingPayment not found with id: " + id));

        existing.setFare(dto.getFare());
        existing.setTax(dto.getTax());
        existing.setServiceFee(dto.getServiceFee());
        existing.setTotalPrice(dto.getTotalPrice());

        BillingPayment updated = billingPaymentRepository.save(existing);
        return BillingPaymentMapper.toDTO(updated);
    }

    // DELETE
    public void deleteBillingPayment(Long id) {
        billingPaymentRepository.deleteById(id);
    }
}