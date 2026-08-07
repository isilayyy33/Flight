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

import com.project.flight.dto.CityDTO;
import com.project.flight.service.CityService;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    public ResponseEntity<CityDTO> createCity(@RequestBody CityDTO dto) {
        CityDTO saved = cityService.saveCity(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CityDTO>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @GetMapping("/{cityCode}")
    public ResponseEntity<CityDTO> getCityByCode(@PathVariable String cityCode) {
        return ResponseEntity.ok(cityService.getCityByCode(cityCode));
    }

    @PutMapping("/{cityCode}")
    public ResponseEntity<CityDTO> updateCity(@PathVariable String cityCode, @RequestBody CityDTO dto) {
        return ResponseEntity.ok(cityService.updateCity(cityCode, dto));
    }

    @DeleteMapping("/{cityCode}")
    public ResponseEntity<Void> deleteCity(@PathVariable String cityCode) {
        cityService.deleteCity(cityCode);
        return ResponseEntity.noContent().build();
    }
}