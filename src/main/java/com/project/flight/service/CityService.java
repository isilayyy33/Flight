package com.project.flight.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.flight.exception.NoDataFoundException;
import com.project.flight.model.City;
import com.project.flight.model.Country;
import com.project.flight.repository.CityRepository;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final CountryService countryService;
    // Depending on CountryService (not CountryRepository directly) keeps this class
    // from reaching into another entity's data layer — any future validation rules
    // added in CountryService automatically apply here too.

    public CityService(CityRepository cityRepository, CountryService countryService) {
        this.cityRepository = cityRepository;
        this.countryService = countryService;
    }

    public City saveCity(City city) {
        // check if a city with this code already exists before creating
        if (cityRepository.existsById(city.getCityCode())) {
            throw new IllegalArgumentException("City already exists with code: " + city.getCityCode());
        }
        // bc city references a Country (a foreign key), verify that country actually exists
        validateCountryExists(city.getCountry());
        return cityRepository.save(city);
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public City getCityByCode(String cityCode) {
        return getCityEntityByCode(cityCode);
    }

    public City updateCity(String cityCode, City updatedCity) {
        City existing = getCityByCode(cityCode);
        // we don't allow attaching a Country that doesn't exist in the system.
        validateCountryExists(updatedCity.getCountry());
        existing.setName(updatedCity.getName());
        existing.setCountry(updatedCity.getCountry());
        return cityRepository.save(existing);
    }

    public void deleteCity(String cityCode) {
        cityRepository.deleteById(cityCode);
    }

    // Helper method: checks that the given country really exists in the system.
    // Delegates to CountryService instead of querying CountryRepository directly.
    // Used by both saveCity and updateCity.
    private void validateCountryExists(Country country) {
        if (country == null || country.getIsoCode() == null) {
            throw new IllegalArgumentException("Country information is required.");
        }
        countryService.getCountryEntityByIsoCode(country.getIsoCode()); // throws NoDataFoundException if missing
    }

    // Single source of truth for "find City by code or throw" —
    // used by getCityByCode, updateCity, and other services (like PortService).
    public City getCityEntityByCode(String cityCode) {
        return cityRepository.findById(cityCode)
                .orElseThrow(() -> new NoDataFoundException("City not found with code: " + cityCode));
    }
}