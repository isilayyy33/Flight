package com.project.flight.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.flight.dto.BillingPaymentDTO;
import com.project.flight.dto.FlightOptionDTO;
import com.project.flight.dto.FlightSearchResultDTO;
import com.project.flight.dto.FlightSegmentDTO;
import com.project.flight.dto.RoundTripOptionDTO;
import com.project.flight.mapper.BillingPaymentMapper;
import com.project.flight.mapper.FlightSegmentMapper;
import com.project.flight.model.BillingPayment;
import com.project.flight.model.FlightSegment;

@Service
public class FlightSearchService {

    private final FlightSegmentService flightSegmentService;
    private final BillingPaymentService billingPaymentService;

    public FlightSearchService(FlightSegmentService flightSegmentService,
                                BillingPaymentService billingPaymentService) {
        this.flightSegmentService = flightSegmentService;
        this.billingPaymentService = billingPaymentService;
    }

    public FlightSearchResultDTO search(String fromPortCode, String toPortCode,
                                         LocalDate flightDate, LocalDate returnDate) {

        if (fromPortCode == null || fromPortCode.isBlank()
                || toPortCode == null || toPortCode.isBlank()
                || flightDate == null) {
            throw new IllegalArgumentException("fromPort, toPort ve flightDate zorunludur.");
        }

        List<FlightSegment> outboundFlights =
                flightSegmentService.findFlightSegments(fromPortCode, toPortCode, flightDate);

        // Tek yön: sadece gidiş seçenekleri
        if (returnDate == null) {
            List<FlightOptionDTO> oneWayOptions = outboundFlights.stream()
                    .map(this::toOption)
                    .collect(Collectors.toList());
            return new FlightSearchResultDTO(oneWayOptions, null);
        }

        // Gidiş-dönüş: dönüş uçuşlarını da bul, gidiş x dönüş kombinasyonlarını oluştur (max 4x4=16)
        List<FlightSegment> returnFlights =
                flightSegmentService.findFlightSegments(toPortCode, fromPortCode, returnDate);

        List<RoundTripOptionDTO> combinations = new ArrayList<>();
        for (FlightSegment outbound : outboundFlights) {
            FlightOptionDTO outboundOption = toOption(outbound);
            for (FlightSegment inbound : returnFlights) {
                FlightOptionDTO returnOption = toOption(inbound);
                BigDecimal totalPrice = outboundOption.getBillingPayment().getTotalPrice()
                        .add(returnOption.getBillingPayment().getTotalPrice());
                combinations.add(new RoundTripOptionDTO(outboundOption, returnOption, totalPrice));
            }
        }

        return new FlightSearchResultDTO(null, combinations);
    }

    private FlightOptionDTO toOption(FlightSegment segment) {
        FlightSegmentDTO segmentDto = FlightSegmentMapper.toDTO(segment);
        BillingPayment billing = billingPaymentService.buildBillingPayment(segment); // DB'ye yazmıyor
        BillingPaymentDTO billingDto = BillingPaymentMapper.toDTO(billing);
        return new FlightOptionDTO(segmentDto, billingDto);
    }
}