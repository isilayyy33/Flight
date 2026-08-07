package com.project.flight.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.flight.dto.PassengerDTO;
import com.project.flight.exception.NoDataFoundException;
import com.project.flight.mapper.PassengerMapper;
import com.project.flight.model.Passenger;
import com.project.flight.model.PassengerTypeEnum;
import com.project.flight.repository.PassengerRepository;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public PassengerTypeEnum determinePassengerType(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("Error:Birth date is required to determine passenger type.");
        }

        int age = Period.between(birthDate, LocalDate.now()).getYears();

        for (PassengerTypeEnum type : PassengerTypeEnum.values()) {
            if (age >= type.getMinAge() && age <= type.getMaxAge()) {
                return type;
            }
        }
        return PassengerTypeEnum.ADT;
    }

    // CREATE
    public PassengerDTO savePassenger(PassengerDTO dto) {
        Passenger passenger = new Passenger();
        passenger.setFirstName(dto.getFirstName());
        passenger.setLastName(dto.getLastName());
        passenger.setPassportNumber(dto.getPassportNumber());
        passenger.setEmail(dto.getEmail());
        passenger.setBirthDate(dto.getBirthDate());
        passenger.setPassengerType(determinePassengerType(dto.getBirthDate()));

        Passenger saved = passengerRepository.save(passenger);
        return PassengerMapper.toDTO(saved);
    }

    // READ - get all
    public List<PassengerDTO> getAllPassengers() {
        return passengerRepository.findAll()
                .stream()
                .map(PassengerMapper::toDTO)
                .collect(Collectors.toList());
    }

    // READ - get one by id
    public PassengerDTO getPassengerById(Long id) {
        return PassengerMapper.toDTO(getPassengerEntityById(id));
    }

    // UPDATE
    public PassengerDTO updatePassenger(Long id, PassengerDTO dto) {
        Passenger existing = getPassengerEntityById(id);

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setPassportNumber(dto.getPassportNumber());
        existing.setEmail(dto.getEmail());
        existing.setBirthDate(dto.getBirthDate());
        existing.setPassengerType(determinePassengerType(dto.getBirthDate()));

        Passenger updated = passengerRepository.save(existing);
        return PassengerMapper.toDTO(updated);
    }

    // DELETE
    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
    }

    // Single source of truth for "find Passenger by id or throw" —
    // used internally here, and by other services (like TicketService) that need a real Passenger entity.
    public Passenger getPassengerEntityById(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException("Passenger not found with id: " + id));
    }
}