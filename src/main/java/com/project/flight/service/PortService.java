package com.project.flight.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.flight.dto.PortDTO;
import com.project.flight.exception.NoDataFoundException;
import com.project.flight.mapper.PortMapper;
import com.project.flight.model.City;
import com.project.flight.model.Port;
import com.project.flight.repository.PortRepository;

@Service
public class PortService {

    private final PortRepository portRepository;
    private final CityService cityService;

    public PortService(PortRepository portRepository, CityService cityService) {
        this.portRepository = portRepository;
        this.cityService = cityService;
    }

    // CREATE
    public PortDTO savePort(PortDTO dto) {
        if (portRepository.existsById(dto.getCode())) {
            throw new IllegalArgumentException("Port already exists with code: " + dto.getCode());
        }
        Port port = buildEntityFromDTO(dto);
        Port saved = portRepository.save(port);
        return PortMapper.toDTO(saved);
    }

    // READ - get all
    public List<PortDTO> getAllPorts() {
        return portRepository.findAll()
                .stream()
                .map(PortMapper::toDTO)
                .collect(Collectors.toList());
    }

    // READ - get one by code
    public PortDTO getPortByCode(String code) {
        return PortMapper.toDTO(getPortEntityByCode(code));
    }

    // UPDATE
    public PortDTO updatePort(String code, PortDTO dto) {
        Port existing = getPortEntityByCode(code);
        existing.setName(dto.getName());
        existing.setCity(findCity(dto.getCityCode()));
        Port updated = portRepository.save(existing);
        return PortMapper.toDTO(updated);
    }

    // DELETE
    public void deletePort(String code) {
        portRepository.deleteById(code);
    }

    private Port buildEntityFromDTO(PortDTO dto) {
        Port port = new Port();
        port.setCode(dto.getCode());
        port.setName(dto.getName());
        port.setCity(findCity(dto.getCityCode()));
        return port;
    }

    // Delegates to CityService instead of querying CityRepository directly.
    private City findCity(String cityCode) {
        return cityService.getCityEntityByCode(cityCode);
    }

    // Single source of truth for "find Port by code or throw" —
    // used internally here, and by other services (like FlightSegmentService) that need a real Port entity.
    public Port getPortEntityByCode(String code) {
        return portRepository.findById(code)
                .orElseThrow(() -> new NoDataFoundException("Port not found with code: " + code));
    }
}