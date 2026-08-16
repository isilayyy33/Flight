package com.project.flight.dto;

import java.util.List;

public class FlightSearchResultDTO {
    private List<FlightOptionDTO> oneWayOptions;       // tek yönse dolu
    private List<RoundTripOptionDTO> roundTripOptions;  // gidiş-dönüşse dolu

    public FlightSearchResultDTO() {}

    public FlightSearchResultDTO(List<FlightOptionDTO> oneWayOptions, List<RoundTripOptionDTO> roundTripOptions) {
        this.oneWayOptions = oneWayOptions;
        this.roundTripOptions = roundTripOptions;
    }

    public List<FlightOptionDTO> getOneWayOptions() { return oneWayOptions; }
    public void setOneWayOptions(List<FlightOptionDTO> oneWayOptions) { this.oneWayOptions = oneWayOptions; }
    public List<RoundTripOptionDTO> getRoundTripOptions() { return roundTripOptions; }
    public void setRoundTripOptions(List<RoundTripOptionDTO> roundTripOptions) { this.roundTripOptions = roundTripOptions; }
}