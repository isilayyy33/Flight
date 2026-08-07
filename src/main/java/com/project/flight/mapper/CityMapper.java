package com.project.flight.mapper;

import com.project.flight.dto.CityDTO;
import com.project.flight.model.City;

public class CityMapper {

    public static CityDTO toDTO(City city) {
        CityDTO dto = new CityDTO();
        dto.setCityCode(city.getCityCode());
        dto.setName(city.getName());
        dto.setCountryIsoCode(city.getCountry().getIsoCode());
        return dto;
    }
}