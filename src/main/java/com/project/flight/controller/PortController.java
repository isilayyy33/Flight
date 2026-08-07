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

import com.project.flight.dto.PortDTO;
import com.project.flight.service.PortService;

@RestController
@RequestMapping("/api/ports")
public class PortController {

    private final PortService portService;

    public PortController(PortService portService) {
        this.portService = portService;
    }

    @PostMapping
    public ResponseEntity<PortDTO> createPort(@RequestBody PortDTO dto) {
        PortDTO saved = portService.savePort(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PortDTO>> getAllPorts() {
        return ResponseEntity.ok(portService.getAllPorts());
    }

    @GetMapping("/{code}")
    public ResponseEntity<PortDTO> getPortByCode(@PathVariable String code) {
        return ResponseEntity.ok(portService.getPortByCode(code));
    }

    @PutMapping("/{code}")
    public ResponseEntity<PortDTO> updatePort(@PathVariable String code, @RequestBody PortDTO dto) {
        return ResponseEntity.ok(portService.updatePort(code, dto));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deletePort(@PathVariable String code) {
        portService.deletePort(code);
        return ResponseEntity.noContent().build();
    }
}