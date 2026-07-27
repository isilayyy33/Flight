package com.project.flight.model;

public enum PassengerTypeEnum {
    ADT("Adult"),
    CHD("Child"),
    INF("Infant");

    private final String label;

    PassengerTypeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}