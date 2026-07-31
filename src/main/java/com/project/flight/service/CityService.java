package com.project.flight.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.flight.model.City;
import com.project.flight.repository.CityRepository;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public City getCityByCode(String cityCode) {
        return cityRepository.findById(cityCode)
                .orElseThrow(() -> new RuntimeException("City not found with code: " + cityCode));
    }

    public City updateCity(String cityCode, City updatedCity) {
        City existing = getCityByCode(cityCode);
        existing.setName(updatedCity.getName());
        existing.setCountry(updatedCity.getCountry());
        return cityRepository.save(existing);
    }

    public void deleteCity(String cityCode) {
        cityRepository.deleteById(cityCode);
    }
}