package com.project.flight.dto;

import java.time.LocalDate;

import com.project.flight.model.PassengerTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String passportNumber;
    private String email;
    private LocalDate birthDate;
    private PassengerTypeEnum passengerType;
}
