package com.project.flight.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.flight.model.FlightSegment;

public interface FlightSegmentRepository extends JpaRepository<FlightSegment, Long> {

    List<FlightSegment> findByDeparturePort_CodeAndArrivalPort_CodeAndDepartureTimeBetween(
            String departurePortCode,
            String arrivalPortCode,
            LocalDateTime start,
            LocalDateTime end
    );
}