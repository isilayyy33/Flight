package com.project.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.flight.model.Port;

public interface PortRepository extends JpaRepository<Port, String> {
}