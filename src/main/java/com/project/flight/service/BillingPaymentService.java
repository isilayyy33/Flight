package com.project.flight.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.flight.dto.BillingPaymentDTO;
import com.project.flight.exception.NoDataFoundException;
import com.project.flight.mapper.BillingPaymentMapper;
import com.project.flight.model.BillingPayment;
import com.project.flight.model.FlightSegment;
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
        return BillingPaymentMapper.toDTO(getBillingPaymentEntityById(id));
    }

    /* READ - get one by id
    public BillingPaymentDTO getBillingPaymentById(Long id) {
        BillingPayment billingPayment = billingPaymentRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException("BillingPayment not found with id: " + id));
        return BillingPaymentMapper.toDTO(billingPayment);
    } */ 

    // UPDATE
    public BillingPaymentDTO updateBillingPayment(Long id, BillingPaymentDTO dto) {
        BillingPayment existing = getBillingPaymentEntityById(id);

        /*  UPDATE
    public BillingPaymentDTO updateBillingPayment(Long id, BillingPaymentDTO dto) {
        BillingPayment existing = billingPaymentRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException("BillingPayment not found with id: " + id));
*/

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

    // Single source of truth for "find BillingPayment by id or throw" —
    // used by getBillingPaymentById, updateBillingPayment, and other services.
    public BillingPayment getBillingPaymentEntityById(Long id) {
        return billingPaymentRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException("BillingPayment not found with id: " + id));
    }


//method to calculate fare, tax, serviceFee and totalPrice for a single FlightSegment

//our constants for fare, tax, and service fee rates for domestic and international flights
    private static final BigDecimal DOMESTIC_FARE = new BigDecimal("100");
    private static final BigDecimal INTERNATIONAL_FARE = new BigDecimal("200");

    private static final BigDecimal DOMESTIC_TAX_RATE = new BigDecimal("0.05");
    private static final BigDecimal INTERNATIONAL_TAX_RATE = new BigDecimal("0.15");

    private static final BigDecimal DOMESTIC_SERVICE_FEE_RATE = new BigDecimal("0.10");
    private static final BigDecimal INTERNATIONAL_SERVICE_FEE_RATE = new BigDecimal("0.15");

    // Calculates fare, tax, serviceFee and totalPrice for a single FlightSegment,
    // based on whether the departure and arrival countries match (domestic) or differ (international).
// Pure hesaplama — DB'ye YAZMAZ. Search/preview için kullanılır.
public BillingPayment buildBillingPayment(FlightSegment flightSegment) {
    boolean isDomestic = isDomesticFlight(flightSegment);
    BigDecimal fare = isDomestic ? DOMESTIC_FARE : INTERNATIONAL_FARE;
    BigDecimal taxRate = isDomestic ? DOMESTIC_TAX_RATE : INTERNATIONAL_TAX_RATE;
    BigDecimal serviceFeeRate = isDomestic ? DOMESTIC_SERVICE_FEE_RATE : INTERNATIONAL_SERVICE_FEE_RATE;
    BigDecimal tax = fare.multiply(taxRate);
    BigDecimal serviceFee = fare.add(tax).multiply(serviceFeeRate);
    BigDecimal totalPrice = fare.add(tax).add(serviceFee);

    BillingPayment billingPayment = new BillingPayment();
    billingPayment.setFare(fare);
    billingPayment.setTax(tax);
    billingPayment.setServiceFee(serviceFee);
    billingPayment.setTotalPrice(totalPrice);
    return billingPayment;
}

// Hesaplar VE kaydeder — sadece kullanıcı gerçekten bilet satın alırken çağrılmalı.
public BillingPayment calculateBillingPayment(FlightSegment flightSegment) {
    return billingPaymentRepository.save(buildBillingPayment(flightSegment));
}

    // Determines if a flight is domestic (same country on both ends) or international.
    private boolean isDomesticFlight(FlightSegment flightSegment) {
        String departureCountry = flightSegment.getDeparturePort().getCity().getCountry().getIsoCode();
        String arrivalCountry = flightSegment.getArrivalPort().getCity().getCountry().getIsoCode();
        return departureCountry.equals(arrivalCountry);
    }
}

