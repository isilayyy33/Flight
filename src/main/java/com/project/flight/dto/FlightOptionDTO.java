package com.project.flight.dto;

public class FlightOptionDTO {
    private FlightSegmentDTO flightSegment;
    private BillingPaymentDTO billingPayment;

    public FlightOptionDTO() {}

    public FlightOptionDTO(FlightSegmentDTO flightSegment, BillingPaymentDTO billingPayment) {
        this.flightSegment = flightSegment;
        this.billingPayment = billingPayment;
    }

    public FlightSegmentDTO getFlightSegment() { return flightSegment; }
    public void setFlightSegment(FlightSegmentDTO flightSegment) { this.flightSegment = flightSegment; }
    public BillingPaymentDTO getBillingPayment() { return billingPayment; }
    public void setBillingPayment(BillingPaymentDTO billingPayment) { this.billingPayment = billingPayment; }
}