package com.project.flight.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "airlines")
@Getter //creates all getters
@Setter //creates all setters
@NoArgsConstructor //creates empty constructor
@AllArgsConstructor //creates constructor with all parameters
public class Airline {

    @Id
    @Column(name = "aa_code", length = 3)
    private String aaCode; // primary key, eskiden iataCode

    @Column(name = "name")
    private String name;

    @Column(name = "numeric_code")
    private int numericCode;

}
