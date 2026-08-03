package com.project.flight.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.project.flight.dto.TicketDTO;
import com.project.flight.model.FlightSegment;
import com.project.flight.model.Ticket;

public class TicketMapper {

    public static TicketDTO toDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setPnrCode(ticket.getPnrCode());
        dto.setPassengerId(ticket.getPassenger().getId());

        List<Long> segmentIds = ticket.getFlightSegments()
                .stream()
                .map(FlightSegment::getId)
                .collect(Collectors.toList());
        dto.setFlightSegmentIds(segmentIds);

        dto.setBillingPaymentId(ticket.getBillingPayment().getId());
        dto.setSeatNumber(ticket.getSeatNumber());
        dto.setPurchaseDate(ticket.getPurchaseDate());
        return dto;
    }
}