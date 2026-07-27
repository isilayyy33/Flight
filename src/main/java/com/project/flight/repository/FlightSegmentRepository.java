package com.project.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.flight.model.FlightSegment;

public interface FlightSegmentRepository extends JpaRepository<FlightSegment, Long> {
}