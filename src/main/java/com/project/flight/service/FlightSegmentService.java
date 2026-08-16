package com.project.flight.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.flight.dto.FlightSegmentDTO;
import com.project.flight.exception.NoDataFoundException;
import com.project.flight.mapper.FlightSegmentMapper;
import com.project.flight.model.Airline;
import com.project.flight.model.FlightSegment;
import com.project.flight.model.Port;
import com.project.flight.repository.FlightSegmentRepository;

@Service
public class FlightSegmentService {

    private final FlightSegmentRepository flightSegmentRepository;
    private final AirlineService airlineService;
    private final PortService portService;
    // Depending on AirlineService/PortService (not their repositories directly) keeps
    // this class from reaching into another entity's data layer.

    public FlightSegmentService(FlightSegmentRepository flightSegmentRepository,
                                 AirlineService airlineService,
                                 PortService portService) {
        this.flightSegmentRepository = flightSegmentRepository;
        this.airlineService = airlineService;
        this.portService = portService;
    }

    // CREATE
    public FlightSegmentDTO saveFlightSegment(FlightSegmentDTO dto) {
        FlightSegment flightSegment = buildEntityFromDTO(dto);
        FlightSegment saved = flightSegmentRepository.save(flightSegment);
        return FlightSegmentMapper.toDTO(saved);
    }

    // READ - get all
    public List<FlightSegmentDTO> getAllFlightSegments() {
        return flightSegmentRepository.findAll()
                .stream()
                .map(FlightSegmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    // READ - get one by id
    public FlightSegmentDTO getFlightSegmentById(Long id) {
        return FlightSegmentMapper.toDTO(getFlightSegmentEntityById(id));
    }

    // UPDATE
    public FlightSegmentDTO updateFlightSegment(Long id, FlightSegmentDTO dto) {
        FlightSegment existing = getFlightSegmentEntityById(id);

        existing.setFlightNumber(dto.getFlightNumber());
        existing.setAirline(findAirline(dto.getAirlineCode()));
        existing.setDeparturePort(findPort(dto.getDeparturePortCode()));
        existing.setArrivalPort(findPort(dto.getArrivalPortCode()));
        existing.setDepartureTime(dto.getDepartureTime());
        existing.setArrivalTime(dto.getArrivalTime());

        FlightSegment updated = flightSegmentRepository.save(existing);
        return FlightSegmentMapper.toDTO(updated);
    }

    // DELETE
    public void deleteFlightSegment(Long id) {
        flightSegmentRepository.deleteById(id);
    }

    private FlightSegment buildEntityFromDTO(FlightSegmentDTO dto) {
        FlightSegment flightSegment = new FlightSegment();
        flightSegment.setFlightNumber(dto.getFlightNumber());
        flightSegment.setAirline(findAirline(dto.getAirlineCode()));
        flightSegment.setDeparturePort(findPort(dto.getDeparturePortCode()));
        flightSegment.setArrivalPort(findPort(dto.getArrivalPortCode()));
        flightSegment.setDepartureTime(dto.getDepartureTime());
        flightSegment.setArrivalTime(dto.getArrivalTime());
        return flightSegment;
    }

    // Now delegates to AirlineService instead of querying AirlineRepository directly.
    private Airline findAirline(String aaCode) {
        return airlineService.getAirlineEntityByCode(aaCode);
    }

    // Now delegates to PortService instead of querying PortRepository directly.
    private Port findPort(String code) {
        return portService.getPortEntityByCode(code);
    }

   
    // Used internally by other services (like TicketService) that need
    // to attach a real FlightSegment entity.
    public FlightSegment getFlightSegmentEntityById(Long id) {
        return flightSegmentRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException("FlightSegment not found with id: " + id));
    }
     // Single source of truth for "find FlightSegment by id or throw" 

     // FlightSearchService tarafından kullanılır: rota + tarihe göre uçuşları bulur.
public List<FlightSegment> findFlightSegments(String departurePortCode, String arrivalPortCode, LocalDate date) {
    LocalDateTime startOfDay = date.atStartOfDay();
    LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
    return flightSegmentRepository.findByDeparturePort_CodeAndArrivalPort_CodeAndDepartureTimeBetween(
            departurePortCode, arrivalPortCode, startOfDay, endOfDay);
}
    
}
