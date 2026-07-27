package com.project.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.flight.model.Airline;

public interface AirlineRepository extends JpaRepository<Airline, String> {
}