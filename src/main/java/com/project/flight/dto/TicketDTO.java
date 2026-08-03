package com.project.flight.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {

    private Long id;
    private String pnrCode;
    private Long passengerId;
    private List<Long> flightSegmentIds;
    private Long billingPaymentId;
    private String seatNumber;
    private LocalDateTime purchaseDate;
}