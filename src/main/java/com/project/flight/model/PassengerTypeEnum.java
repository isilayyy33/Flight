package com.project.flight.model;

public enum PassengerTypeEnum {
    ADT("Adult", 12, 120),
    CHD("Child", 2, 11),
    INF("Infant", 0, 1);

    private final String label;
    private final int minAge;
    private final int maxAge;

    PassengerTypeEnum(String label, int minAge, int maxAge) {
        this.label = label;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public String getLabel() {
        return label;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }
}