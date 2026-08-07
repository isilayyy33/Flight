package com.project.flight.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.flight.dto.CountryDTO;
import com.project.flight.exception.NoDataFoundException;
import com.project.flight.mapper.CountryMapper;
import com.project.flight.model.Country;
import com.project.flight.repository.CountryRepository;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    // CREATE
    public CountryDTO saveCountry(CountryDTO dto) {
        if (countryRepository.existsById(dto.getIsoCode())) {
            throw new IllegalArgumentException("Country already exists with isoCode: " + dto.getIsoCode());
        }
        Country country = new Country();
        country.setIsoCode(dto.getIsoCode());
        country.setName(dto.getName());
        Country saved = countryRepository.save(country);
        return CountryMapper.toDTO(saved);
    }

    // READ - get all
    public List<CountryDTO> getAllCountries() {
        return countryRepository.findAll()
                .stream()
                .map(CountryMapper::toDTO)
                .collect(Collectors.toList());
    }

    // READ - get one by isoCode
    public CountryDTO getCountryByIsoCode(String isoCode) {
        return CountryMapper.toDTO(getCountryEntityByIsoCode(isoCode));
    }

    // UPDATE
    public CountryDTO updateCountry(String isoCode, CountryDTO dto) {
        Country existing = getCountryEntityByIsoCode(isoCode);
        existing.setName(dto.getName());
        Country updated = countryRepository.save(existing);
        return CountryMapper.toDTO(updated);
    }

    // DELETE
    public void deleteCountry(String isoCode) {
        countryRepository.deleteById(isoCode);
    }

    // Single source of truth for "find Country by isoCode or throw" —
    // used internally here, and by other services (like CityService) that need a real Country entity.
    public Country getCountryEntityByIsoCode(String isoCode) {
        return countryRepository.findById(isoCode)
                .orElseThrow(() -> new NoDataFoundException("Country not found with isoCode: " + isoCode));
    }
}