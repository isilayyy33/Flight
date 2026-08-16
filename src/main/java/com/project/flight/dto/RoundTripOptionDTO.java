package com.project.flight.dto;

import java.math.BigDecimal;

public class RoundTripOptionDTO {
    private FlightOptionDTO outbound;   // Gidiş
    private FlightOptionDTO returnFlight; // Dönüş
    private BigDecimal totalPrice;      // ikisinin toplamı

    public RoundTripOptionDTO() {}

    public RoundTripOptionDTO(FlightOptionDTO outbound, FlightOptionDTO returnFlight, BigDecimal totalPrice) {
        this.outbound = outbound;
        this.returnFlight = returnFlight;
        this.totalPrice = totalPrice;
    }

    public FlightOptionDTO getOutbound() { return outbound; }
    public void setOutbound(FlightOptionDTO outbound) { this.outbound = outbound; }
    public FlightOptionDTO getReturnFlight() { return returnFlight; }
    public void setReturnFlight(FlightOptionDTO returnFlight) { this.returnFlight = returnFlight; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
}