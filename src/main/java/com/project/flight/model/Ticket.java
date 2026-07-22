package com.project.flight.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pnrCode;

    private String passengerName;

    private String flightSegment;

    private int totalPrice;

    private int tax;

    private int seatNumber;

    private String purchaseDate;

    public Ticket() {
    }

    public Ticket(String pnrCode, String passengerName, String flightSegment,
                  int totalPrice, int tax, int seatNumber, String purchaseDate) {
        this.pnrCode = pnrCode;
        this.passengerName = passengerName;
        this.flightSegment = flightSegment;
        this.totalPrice = totalPrice;
        this.tax = tax;
        this.seatNumber = seatNumber;
        this.purchaseDate = purchaseDate;
    }

    public Long getId() {
        return id;
    }

    public String getPnrCode() {
        return pnrCode;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getFlightSegment() {
        return flightSegment;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getTax() {
        return tax;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPnrCode(String pnrCode) {
        this.pnrCode = pnrCode;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public void setFlightSegment(String flightSegment) {
        this.flightSegment = flightSegment;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}