package com.project.flight.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.flight.model.Airline;
import com.project.flight.service.AirlineService;

@RestController
@RequestMapping("/api/airlines")
public class AirlineController {

    private final AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    // POST /api/airlines
    @PostMapping
    public ResponseEntity<Airline> createAirline(@RequestBody Airline airline) {
        Airline saved = airlineService.saveAirline(airline);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // GET /api/airlines
    @GetMapping
    public ResponseEntity<List<Airline>> getAllAirlines() {
        return ResponseEntity.ok(airlineService.getAllAirlines());
    }

    // GET /api/airlines/{aaCode}
    @GetMapping("/{aaCode}")
    public ResponseEntity<Airline> getAirlineById(@PathVariable String aaCode) {
        return ResponseEntity.ok(airlineService.getAirlineById(aaCode));
    }

    // PUT /api/airlines/{aaCode}
    @PutMapping("/{aaCode}")
    public ResponseEntity<Airline> updateAirline(@PathVariable String aaCode, @RequestBody Airline updatedAirline) {
        return ResponseEntity.ok(airlineService.updateAirline(aaCode, updatedAirline));
    }

    // DELETE /api/airlines/{aaCode}
    @DeleteMapping("/{aaCode}")
    public ResponseEntity<Void> deleteAirline(@PathVariable String aaCode) {
        airlineService.deleteAirline(aaCode);
        return ResponseEntity.noContent().build();
    }
}