package com.project.flight.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.flight.exception.NoDataFoundException;
import com.project.flight.model.City;
import com.project.flight.model.Country;
import com.project.flight.repository.CityRepository;
import com.project.flight.repository.CountryRepository;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    // CountryRepository added so we can verify that a referenced country actually exists
    // before allowing a city to be created 

    public CityService(CityRepository cityRepository, CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
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
        return cityRepository.findById(cityCode)
                .orElseThrow(() -> new NoDataFoundException("City not found with code: " + cityCode));
    }

    public City updateCity(String cityCode, City updatedCity) {
        City existing = getCityByCode(cityCode);
        //  we don't allow attaching a Country that doesn't exist in the system.
        validateCountryExists(updatedCity.getCountry());
        existing.setName(updatedCity.getName());
        existing.setCountry(updatedCity.getCountry());
        return cityRepository.save(existing);
    }

    public void deleteCity(String cityCode) {
        cityRepository.deleteById(cityCode);
    }

    // Helper method: checks that the given country really exists in the system
    // Used by both saveCity and updateCity 
    private void validateCountryExists(Country country) {
        if (country == null || country.getIsoCode() == null) {
            throw new IllegalArgumentException("Country information is required.");
        }
        if (!countryRepository.existsById(country.getIsoCode())) {
            throw new IllegalArgumentException("No such country exists with isoCode: " + country.getIsoCode());
        }
    }
}
