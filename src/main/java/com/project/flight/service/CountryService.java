package com.project.flight.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.flight.model.Country;
import com.project.flight.repository.CountryRepository;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country getCountryByIsoCode(String isoCode) {
        return countryRepository.findById(isoCode)
                .orElseThrow(() -> new RuntimeException("Country not found with isoCode: " + isoCode));
    }

    public Country updateCountry(String isoCode, Country updatedCountry) {
        Country existing = getCountryByIsoCode(isoCode);
        existing.setName(updatedCountry.getName());
        return countryRepository.save(existing);
    }

    public void deleteCountry(String isoCode) {
        countryRepository.deleteById(isoCode);
    }
}