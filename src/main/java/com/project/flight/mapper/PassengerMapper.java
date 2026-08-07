package com.project.flight.mapper;

import com.project.flight.dto.PassengerDTO;
import com.project.flight.model.Passenger;

public class PassengerMapper {

    public static PassengerDTO toDTO(Passenger passenger) {
        PassengerDTO dto = new PassengerDTO();
        dto.setId(passenger.getId());
        dto.setFirstName(passenger.getFirstName());
        dto.setLastName(passenger.getLastName());
        dto.setPassportNumber(passenger.getPassportNumber());
        dto.setEmail(passenger.getEmail());
        dto.setBirthDate(passenger.getBirthDate());
        dto.setPassengerType(passenger.getPassengerType());
        return dto;
    }
}