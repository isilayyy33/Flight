package com.project.flight.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*If I'd used @Data instead of @Entity 
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor , I'd only need one import (import lombok.Data;), 
since it's a single annotation it just happens to bundle several behaviors internally, 
but that doesn't change how many imports it needs on my end.     */

@Entity //Says it is a table in the database
@Getter //creates all getters
@Setter //creates all setters
@NoArgsConstructor //creates empty constructor
@AllArgsConstructor //creates constructor with all parameters
public class Airline {

    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //tells JPA/Hibernate how to automatically generate the primary key value when you save a new entity — so I never have to set id myself.
    private Long id;

    private String name;

    private String iataCode;

    private int numericCode;
}