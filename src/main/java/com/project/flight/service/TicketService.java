package com.project.flight.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.flight.dto.TicketDTO;
import com.project.flight.exception.NoDataFoundException;
import com.project.flight.mapper.TicketMapper;
import com.project.flight.model.BillingPayment;
import com.project.flight.model.FlightSegment;
import com.project.flight.model.Passenger;
import com.project.flight.model.Ticket;
import com.project.flight.repository.TicketRepository;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final PassengerService passengerService;
    private final FlightSegmentService flightSegmentService;
    private final BillingPaymentService billingPaymentService;
    // Depending on the owning Services (not their repositories directly) keeps
    // this class from reaching into another entity's data layer.

    public TicketService(TicketRepository ticketRepository,
                          PassengerService passengerService,
                          FlightSegmentService flightSegmentService,
                          BillingPaymentService billingPaymentService) {
        this.ticketRepository = ticketRepository;
        this.passengerService = passengerService;
        this.flightSegmentService = flightSegmentService;
        this.billingPaymentService = billingPaymentService;
    }

    // CREATE
    public TicketDTO saveTicket(TicketDTO dto) {
        Ticket ticket = buildEntityFromDTO(dto);
        Ticket saved = ticketRepository.save(ticket);
        return TicketMapper.toDTO(saved);
    }

    // READ - get all
    public List<TicketDTO> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(TicketMapper::toDTO)
                .collect(Collectors.toList());
    }

    // READ - get one by id
    // Delegates to getTicketEntityById instead of repeating the same query here —
    // keeps the "Ticket not found" lookup logic in exactly one place.
    public TicketDTO getTicketById(Long id) {
        return TicketMapper.toDTO(getTicketEntityById(id));
    }

    // UPDATE
    public TicketDTO updateTicket(Long id, TicketDTO dto) {
        Ticket existing = getTicketEntityById(id);

        existing.setPnrCode(dto.getPnrCode());
        existing.setPassenger(findPassenger(dto.getPassengerId()));
        existing.setFlightSegments(findFlightSegments(dto.getFlightSegmentIds()));
        existing.setBillingPayment(findBillingPayment(dto.getBillingPaymentId()));
        existing.setSeatNumber(dto.getSeatNumber());
        existing.setPurchaseDate(dto.getPurchaseDate());

        Ticket updated = ticketRepository.save(existing);
        return TicketMapper.toDTO(updated);
    }

    // DELETE
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    private Ticket buildEntityFromDTO(TicketDTO dto) {
        Ticket ticket = new Ticket();
        ticket.setPnrCode(dto.getPnrCode());
        ticket.setPassenger(findPassenger(dto.getPassengerId()));
        ticket.setFlightSegments(findFlightSegments(dto.getFlightSegmentIds()));
        ticket.setBillingPayment(findBillingPayment(dto.getBillingPaymentId()));
        ticket.setSeatNumber(dto.getSeatNumber());
        ticket.setPurchaseDate(dto.getPurchaseDate());
        return ticket;
    }

    // Now delegates to PassengerService instead of querying PassengerRepository directly.
    private Passenger findPassenger(Long passengerId) {
        return passengerService.getPassengerEntityById(passengerId);
    }

    // Now delegates to BillingPaymentService instead of querying BillingPaymentRepository directly.
    private BillingPayment findBillingPayment(Long billingPaymentId) {
        return billingPaymentService.getBillingPaymentEntityById(billingPaymentId);
    }

    // Now delegates to FlightSegmentService instead of querying FlightSegmentRepository directly.
    private List<FlightSegment> findFlightSegments(List<Long> flightSegmentIds) {
        return flightSegmentIds.stream()
                .map(flightSegmentService::getFlightSegmentEntityById)
                .collect(Collectors.toList());
    }

    // Single source of truth for "find Ticket by id or throw" 
    // used by getTicketById and updateTicket.
    private Ticket getTicketEntityById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException("Ticket not found with id: " + id));
    }
}