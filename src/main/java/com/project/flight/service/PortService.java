package com.project.flight.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.flight.exception.NoDataFoundException;
import com.project.flight.model.City;
import com.project.flight.model.Port;
import com.project.flight.repository.CityRepository;
import com.project.flight.repository.PortRepository;

@Service
public class PortService {

    private final PortRepository portRepository;
    private final CityRepository cityRepository;
    // CityRepository added so we can verify that a referenced city actually exists
    // before allowing a port to be created or updated

    public PortService(PortRepository portRepository, CityRepository cityRepository) {
        this.portRepository = portRepository;
        this.cityRepository = cityRepository;
    }

    public Port savePort(Port port) {
        // we check if a Port with this code already exists before creating
        // bc otherwise save() would silently overwrite the existing record instead of creating a new one
        if (portRepository.existsById(port.getCode())) {
            throw new IllegalArgumentException("Port already exists with code: " + port.getCode());
        }
        // Since port references a city (a foreign key), verify that city actually exists in the system.
        validateCityExists(port.getCity());
        return portRepository.save(port);
    }

    public List<Port> getAllPorts() {
        return portRepository.findAll();
    }

    public Port getPortByCode(String code) {
        return portRepository.findById(code)
                .orElseThrow(() -> new NoDataFoundException("Port not found with code: " + code));
    }

    public Port updatePort(String code, Port updatedPort) {
        Port existing = getPortByCode(code);
        //we don't allow attaching a city that doesn't exist in the system
        validateCityExists(updatedPort.getCity());
        existing.setName(updatedPort.getName());
        existing.setCity(updatedPort.getCity());
        return portRepository.save(existing);
    }

    public void deletePort(String code) {
        portRepository.deleteById(code);
    }

    // Helper method: checks that the given city really exists in the system
    // Used by both savePort and updatePort
    private void validateCityExists(City city) {
        if (city == null || city.getCityCode() == null) {
            throw new IllegalArgumentException("City information is required.");
        }
        if (!cityRepository.existsById(city.getCityCode())) {
            throw new IllegalArgumentException("No such city exists with code: " + city.getCityCode());
        }
    }
}