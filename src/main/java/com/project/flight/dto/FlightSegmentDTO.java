package com.project.flight.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightSegmentDTO {

    private Long id;
    private String flightNumber;
    private String airlineCode;
    private String departurePortCode;
    private String arrivalPortCode;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
}