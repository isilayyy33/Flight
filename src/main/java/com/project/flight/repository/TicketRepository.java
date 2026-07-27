package com.project.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.flight.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}