package com.project.flight.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity  //Says it is a table in the database
public class Airline {

    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //automatic ID increase
    private Long id;

    private String name;

    private String iataCode;

    private int numericCode;

    public Airline() {
    }

    //JPA (Java Persistence API), Java nesnelerini (object) veritabanındaki tablolara (table) dönüştürmeye yarayan bir teknolojidir.
    // "Hibernate"
    public Airline(String name, String iataCode, int numericCode) {
        this.name = name;
        this.iataCode = iataCode;
        this.numericCode = numericCode;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIataCode() {
        return iataCode;
    }

    public int getNumericCode() {
        return numericCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public void setNumericCode(int numericCode) {
        this.numericCode = numericCode;
    }
}