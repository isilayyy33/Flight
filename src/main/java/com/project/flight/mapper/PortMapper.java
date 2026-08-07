package com.project.flight.mapper;

import com.project.flight.dto.PortDTO;
import com.project.flight.model.Port;

public class PortMapper {

    public static PortDTO toDTO(Port port) {
        PortDTO dto = new PortDTO();
        dto.setCode(port.getCode());
        dto.setName(port.getName());
        dto.setCityCode(port.getCity().getCityCode());
        return dto;
    }
}