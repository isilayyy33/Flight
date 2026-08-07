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

import com.project.flight.dto.FlightSegmentDTO;
import com.project.flight.service.FlightSegmentService;

@RestController
@RequestMapping("/api/flight-segments")
public class FlightSegmentController {

    private final FlightSegmentService flightSegmentService;

    public FlightSegmentController(FlightSegmentService flightSegmentService) {
        this.flightSegmentService = flightSegmentService;
    }

    // POST /api/flight-segments
    @PostMapping
    public ResponseEntity<FlightSegmentDTO> createFlightSegment(@RequestBody FlightSegmentDTO dto) {
        FlightSegmentDTO saved = flightSegmentService.saveFlightSegment(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // GET /api/flight-segments
    @GetMapping
    public ResponseEntity<List<FlightSegmentDTO>> getAllFlightSegments() {
        return ResponseEntity.ok(flightSegmentService.getAllFlightSegments());
    }

    // GET /api/flight-segments/{id}
    @GetMapping("/{id}")
    public ResponseEntity<FlightSegmentDTO> getFlightSegmentById(@PathVariable Long id) {
        return ResponseEntity.ok(flightSegmentService.getFlightSegmentById(id));
    }

    // PUT /api/flight-segments/{id}
    @PutMapping("/{id}")
    public ResponseEntity<FlightSegmentDTO> updateFlightSegment(@PathVariable Long id, @RequestBody FlightSegmentDTO dto) {
        return ResponseEntity.ok(flightSegmentService.updateFlightSegment(id, dto));
    }

    // DELETE /api/flight-segments/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlightSegment(@PathVariable Long id) {
        flightSegmentService.deleteFlightSegment(id);
        return ResponseEntity.noContent().build();
    }
}
