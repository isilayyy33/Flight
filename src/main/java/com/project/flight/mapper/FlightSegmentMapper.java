package com.project.flight.mapper;

import com.project.flight.dto.FlightSegmentDTO;
import com.project.flight.model.FlightSegment;

public class FlightSegmentMapper {

    public static FlightSegmentDTO toDTO(FlightSegment flightSegment) {
        FlightSegmentDTO dto = new FlightSegmentDTO();
        dto.setId(flightSegment.getId());
        dto.setFlightNumber(flightSegment.getFlightNumber());
        dto.setAirlineCode(flightSegment.getAirline().getAaCode());
        dto.setDeparturePortCode(flightSegment.getDeparturePort().getCode());
        dto.setArrivalPortCode(flightSegment.getArrivalPort().getCode());
        dto.setDepartureTime(flightSegment.getDepartureTime());
        dto.setArrivalTime(flightSegment.getArrivalTime());
        return dto;
    }
}