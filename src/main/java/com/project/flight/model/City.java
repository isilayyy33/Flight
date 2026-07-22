package com.project.flight.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int licensePlateNumber;

    private int timezone;

    private String country;

    public City() {
    }

    public City(String name, int licensePlateNumber, int timezone, String country) {
        this.name = name;
        this.licensePlateNumber = licensePlateNumber;
        this.timezone = timezone;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public int getTimezone() {
        return timezone;
    }

    public String getCountry() {
        return country;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLicensePlateNumber(int licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}