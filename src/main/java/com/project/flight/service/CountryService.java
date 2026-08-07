package com.project.flight.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.flight.exception.NoDataFoundException;
import com.project.flight.model.Country;
import com.project.flight.repository.CountryRepository;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Country saveCountry(Country country) {
        // Check if a Country with this isoCode already exists before creating,
        // otherwise save() would silently overwrite the existing record instead of creating a new one.
        if (countryRepository.existsById(country.getIsoCode())) {
            throw new IllegalArgumentException("Country already exists with isoCode: " + country.getIsoCode());
        }
        return countryRepository.save(country);
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country getCountryByIsoCode(String isoCode) {
        return getCountryEntityByIsoCode(isoCode);
    }

    public Country updateCountry(String isoCode, Country updatedCountry) {
        Country existing = getCountryByIsoCode(isoCode);
        existing.setName(updatedCountry.getName());
        return countryRepository.save(existing);
    }

    public void deleteCountry(String isoCode) {
        countryRepository.deleteById(isoCode);
    }

    // Single source of truth for "find Country by isoCode or throw" —
    // used by getCountryByIsoCode, updateCountry, and other services (like CityService).
    public Country getCountryEntityByIsoCode(String isoCode) {
        return countryRepository.findById(isoCode)
                .orElseThrow(() -> new NoDataFoundException("Country not found with isoCode: " + isoCode));
    }
}