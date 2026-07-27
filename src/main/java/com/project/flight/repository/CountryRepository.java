package com.project.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.flight.model.Country;

public interface CountryRepository extends JpaRepository<Country, String> {
}