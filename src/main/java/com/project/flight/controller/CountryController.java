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

import com.project.flight.dto.CountryDTO;
import com.project.flight.service.CountryService;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping
    public ResponseEntity<CountryDTO> createCountry(@RequestBody CountryDTO dto) {
        CountryDTO saved = countryService.saveCountry(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CountryDTO>> getAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    @GetMapping("/{isoCode}")
    public ResponseEntity<CountryDTO> getCountryByIsoCode(@PathVariable String isoCode) {
        return ResponseEntity.ok(countryService.getCountryByIsoCode(isoCode));
    }

    @PutMapping("/{isoCode}")
    public ResponseEntity<CountryDTO> updateCountry(@PathVariable String isoCode, @RequestBody CountryDTO dto) {
        return ResponseEntity.ok(countryService.updateCountry(isoCode, dto));
    }

    @DeleteMapping("/{isoCode}")
    public ResponseEntity<Void> deleteCountry(@PathVariable String isoCode) {
        countryService.deleteCountry(isoCode);
        return ResponseEntity.noContent().build();
    }
}
