package com.project.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.flight.model.City;

public interface CityRepository extends JpaRepository<City, String> {
}