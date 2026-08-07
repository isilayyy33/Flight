package com.project.flight.mapper;

import com.project.flight.dto.CountryDTO;
import com.project.flight.model.Country;

public class CountryMapper {

    public static CountryDTO toDTO(Country country) {
        CountryDTO dto = new CountryDTO();
        dto.setIsoCode(country.getIsoCode());
        dto.setName(country.getName());
        return dto;
    }
}