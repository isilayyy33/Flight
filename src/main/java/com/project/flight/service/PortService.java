package com.project.flight.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.flight.model.Port;
import com.project.flight.repository.PortRepository;

@Service
public class PortService {

    private final PortRepository portRepository;

    public PortService(PortRepository portRepository) {
        this.portRepository = portRepository;
    }

    public Port savePort(Port port) {
        return portRepository.save(port);
    }

    public List<Port> getAllPorts() {
        return portRepository.findAll();
    }

    public Port getPortByCode(String code) {
        return portRepository.findById(code)
                .orElseThrow(() -> new RuntimeException("Port not found with code: " + code));
    }

    public Port updatePort(String code, Port updatedPort) {
        Port existing = getPortByCode(code);
        existing.setName(updatedPort.getName());
        existing.setCity(updatedPort.getCity());
        return portRepository.save(existing);
    }

    public void deletePort(String code) {
        portRepository.deleteById(code);
    }
}