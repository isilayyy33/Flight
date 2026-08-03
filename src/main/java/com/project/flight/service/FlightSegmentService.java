package com.project.flight.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;   /*This annotation tells Spring to treat this class as a service component, as a bean, making it eligible for dependency injection and other Spring features.*/

import com.project.flight.dto.FlightSegmentDTO;
import com.project.flight.exception.NoDataFoundException; 
import com.project.flight.mapper.FlightSegmentMapper;
import com.project.flight.model.Airline;
import com.project.flight.model.FlightSegment;
import com.project.flight.model.Port;
import com.project.flight.repository.AirlineRepository;
import com.project.flight.repository.FlightSegmentRepository;
import com.project.flight.repository.PortRepository;

@Service
public class FlightSegmentService {

    private final FlightSegmentRepository flightSegmentRepository; /*we writw "final" because we don't want it to be changed after initialization */
    private final AirlineRepository airlineRepository;
    private final PortRepository portRepository;

    public FlightSegmentService(FlightSegmentRepository flightSegmentRepository,
                                 AirlineRepository airlineRepository,
                                 PortRepository portRepository) {
        this.flightSegmentRepository = flightSegmentRepository;
        this.airlineRepository = airlineRepository;
        this.portRepository = portRepository;
    }

    // CREATE
    public FlightSegmentDTO saveFlightSegment(FlightSegmentDTO dto) {
        FlightSegment flightSegment = buildEntityFromDTO(dto);
        FlightSegment saved = flightSegmentRepository.save(flightSegment);
        return FlightSegmentMapper.toDTO(saved);
    }

    // READ 
    public List<FlightSegmentDTO> getAllFlightSegments() {
        return flightSegmentRepository.findAll()
                .stream()
                .map(FlightSegmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    // READ 
    public FlightSegmentDTO getFlightSegmentById(Long id) {
        FlightSegment flightSegment = flightSegmentRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException("FlightSegment not found with id: " + id));
        return FlightSegmentMapper.toDTO(flightSegment);
    }

    // UPDATE
    public FlightSegmentDTO updateFlightSegment(Long id, FlightSegmentDTO dto) {
        FlightSegment existing = flightSegmentRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException("FlightSegment not found with id: " + id));

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

    private Airline findAirline(String aaCode) {
        return airlineRepository.findById(aaCode)
                .orElseThrow(() -> new NoDataFoundException("Airline not found with code: " + aaCode));
    }

    private Port findPort(String code) {
        return portRepository.findById(code)
                .orElseThrow(() -> new NoDataFoundException("Port not found with code: " + code));
    }
}