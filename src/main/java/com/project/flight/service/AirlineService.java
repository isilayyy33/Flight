package com.project.flight.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.flight.exception.NoDataFoundException;
import com.project.flight.model.Airline;
import com.project.flight.repository.AirlineRepository;

@Service
public class AirlineService {

    private final AirlineRepository airlineRepository;

    public AirlineService(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    public Airline saveAirline(Airline airline) {
        // Check if an Airline with this code already exists before creating,
        // otherwise save() would silently overwrite the existing record instead of creating a new one.
        if (airlineRepository.existsById(airline.getAaCode())) {
            throw new IllegalArgumentException("Airline already exists with code: " + airline.getAaCode());
        }
        return airlineRepository.save(airline);
    }

    public List<Airline> getAllAirlines() {
        return airlineRepository.findAll();
    }

    public Airline getAirlineById(String aaCode) {
        return airlineRepository.findById(aaCode)
                .orElseThrow(() -> new NoDataFoundException("Airline not found with code: " + aaCode));
    }

    public Airline updateAirline(String aaCode, Airline updatedAirline) {
        Airline existing = getAirlineById(aaCode);
        existing.setName(updatedAirline.getName());
        existing.setNumericCode(updatedAirline.getNumericCode());
        return airlineRepository.save(existing);
    }

    public void deleteAirline(String aaCode) {
        airlineRepository.deleteById(aaCode);
    }
}