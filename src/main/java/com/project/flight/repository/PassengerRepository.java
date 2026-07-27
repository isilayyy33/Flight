package com.project.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.flight.model.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}