package com.project.flight.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.flight.dto.FlightSearchResultDTO;
import com.project.flight.service.FlightSearchService;

@RestController
@RequestMapping("/api/flights")
public class FlightSearchController {

    private final FlightSearchService flightSearchService;

    public FlightSearchController(FlightSearchService flightSearchService) {
        this.flightSearchService = flightSearchService;
    }

    // GET /api/flights/search?fromPort=IST&toPort=AYT&flightDate=2026-09-18&returnDate=2026-09-25
    @GetMapping("/search")
    public ResponseEntity<FlightSearchResultDTO> search(
            @RequestParam String fromPort,
            @RequestParam String toPort,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate flightDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate) {

        return ResponseEntity.ok(flightSearchService.search(fromPort, toPort, flightDate, returnDate));
    }
}