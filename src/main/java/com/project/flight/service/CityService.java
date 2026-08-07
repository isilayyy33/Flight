package com.project.flight.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.flight.dto.CityDTO;
import com.project.flight.exception.NoDataFoundException;
import com.project.flight.mapper.CityMapper;
import com.project.flight.model.City;
import com.project.flight.model.Country;
import com.project.flight.repository.CityRepository;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final CountryService countryService;

    public CityService(CityRepository cityRepository, CountryService countryService) {
        this.cityRepository = cityRepository;
        this.countryService = countryService;
    }

    // CREATE
    public CityDTO saveCity(CityDTO dto) {
        if (cityRepository.existsById(dto.getCityCode())) {
            throw new IllegalArgumentException("City already exists with code: " + dto.getCityCode());
        }
        City city = buildEntityFromDTO(dto);
        City saved = cityRepository.save(city);
        return CityMapper.toDTO(saved);
    }

    // READ - get all
    public List<CityDTO> getAllCities() {
        return cityRepository.findAll()
                .stream()
                .map(CityMapper::toDTO)
                .collect(Collectors.toList());
    }

    // READ - get one by code
    public CityDTO getCityByCode(String cityCode) {
        return CityMapper.toDTO(getCityEntityByCode(cityCode));
    }

    // UPDATE
    public CityDTO updateCity(String cityCode, CityDTO dto) {
        City existing = getCityEntityByCode(cityCode);
        existing.setName(dto.getName());
        existing.setCountry(findCountry(dto.getCountryIsoCode()));
        City updated = cityRepository.save(existing);
        return CityMapper.toDTO(updated);
    }

    // DELETE
    public void deleteCity(String cityCode) {
        cityRepository.deleteById(cityCode);
    }

    private City buildEntityFromDTO(CityDTO dto) {
        City city = new City();
        city.setCityCode(dto.getCityCode());
        city.setName(dto.getName());
        city.setCountry(findCountry(dto.getCountryIsoCode()));
        return city;
    }

    // Delegates to CountryService instead of querying CountryRepository directly.
    private Country findCountry(String isoCode) {
        return countryService.getCountryEntityByIsoCode(isoCode);
    }

    // Single source of truth for "find City by code or throw" —
    // used internally here, and by other services (like PortService) that need a real City entity.
    public City getCityEntityByCode(String cityCode) {
        return cityRepository.findById(cityCode)
                .orElseThrow(() -> new NoDataFoundException("City not found with code: " + cityCode));
    }
}