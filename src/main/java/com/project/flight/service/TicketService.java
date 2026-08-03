package com.project.flight.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.flight.dto.TicketDTO;
import com.project.flight.mapper.TicketMapper;
import com.project.flight.model.BillingPayment;
import com.project.flight.model.FlightSegment;
import com.project.flight.model.Passenger;
import com.project.flight.model.Ticket;
import com.project.flight.repository.BillingPaymentRepository;
import com.project.flight.repository.FlightSegmentRepository;
import com.project.flight.repository.PassengerRepository;
import com.project.flight.repository.TicketRepository;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;
    private final FlightSegmentRepository flightSegmentRepository;
    private final BillingPaymentRepository billingPaymentRepository;

    public TicketService(TicketRepository ticketRepository,
                          PassengerRepository passengerRepository,
                          FlightSegmentRepository flightSegmentRepository,
                          BillingPaymentRepository billingPaymentRepository) {
        this.ticketRepository = ticketRepository;
        this.passengerRepository = passengerRepository;
        this.flightSegmentRepository = flightSegmentRepository;
        this.billingPaymentRepository = billingPaymentRepository;
    }

    // CREATE
    public TicketDTO saveTicket(TicketDTO dto) {
        Ticket ticket = buildEntityFromDTO(dto);
        Ticket saved = ticketRepository.save(ticket);
        return TicketMapper.toDTO(saved);
    }

    // READ - hepsini getir
    public List<TicketDTO> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(TicketMapper::toDTO)
                .collect(Collectors.toList());
    }

    // READ - id'ye göre tek kayıt
    public TicketDTO getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
        return TicketMapper.toDTO(ticket);
    }

    // UPDATE
    public TicketDTO updateTicket(Long id, TicketDTO dto) {
        Ticket existing = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));

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

    // Yardımcı metod: DTO'dan gelen id'lerle gerçek Entity nesnesi kuruyor
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

    private Passenger findPassenger(Long passengerId) {
        return passengerRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passenger not found with id: " + passengerId));
    }

    private BillingPayment findBillingPayment(Long billingPaymentId) {
        return billingPaymentRepository.findById(billingPaymentId)
                .orElseThrow(() -> new RuntimeException("BillingPayment not found with id: " + billingPaymentId));
    }

    private List<FlightSegment> findFlightSegments(List<Long> flightSegmentIds) {
        return flightSegmentIds.stream()
                .map(id -> flightSegmentRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("FlightSegment not found with id: " + id)))
                .collect(Collectors.toList());
    }
}