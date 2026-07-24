package com.project.flight.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pnr_code", nullable = false, length = 6)
    private String pnrCode;

    @OneToOne
    private Passenger passenger;

    @ManyToMany
    private List<FlightSegment> flightSegments;


    @ManyToOne
    @JoinColumn(name = "billing_payment_id")
    private BillingPayment billingPayment;

    @Column(name = "seat_number")
    private String seatNumber;  //bu int olmaz cunki 15H falan olabilir seat 

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate; /*A purchase happens at a specific date and time, so LocalDateTime (not LocalDate):
String is just text — Java doesn't know it's a date, so it can't validate it, compare it correctly, do date math on it, or sort it chronologically. Any word would be just as "valid" as "2026-07-24".
LocalDateTime is an actual date/time type — Java understands it as a real point in time. That means
It can't hold an invalid date like "July 35th" or something like that
we can compare/subtract dates directly with isBefore(), isAfter(), etc.
Sorting works chronologically, not alphabetically
No format confusion ("07/24/2026" vs "24.07.2026")
Basically weuse the type that matches what the data actually is, so the compiler and database enforce correctness for you instead of you hoping every string is formatted right. */
}