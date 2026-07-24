package com.project.flight.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "flight_segments")
/*If I wrote @Data instead of the 4 below it would generate @Getter @Setter but not @NoArgsConstructor and @AllArgsConstructor.
And @Data would generate equals and hashcode methods which might be a problem down the road on entity classes and relationships.

"@ToString will try to print every field, including @ManyToOne related entities. Printing a Port will try to print its City, 
which if that also has a @ToString... you can get infinite loops, or at minimum accidentally trigger lazy-loading of related 
data at the wrong time (extra DB queries, or LazyInitializationException if the session's closed).
@EqualsAndHashCode by default uses all fields too, which is unstable for JPA entities — an entity's identity should really be 
based on id alone, and computing hashCode from a mutable relationship graph can break things like storing entities in a HashSet."

So I used the 4 below instead of @Data. */

@Getter
@Setter
@NoArgsConstructor //creates empty constructor
@AllArgsConstructor //creates constructor with all parameters
public class FlightSegment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flight_number")
    private String flightNumber;

    @ManyToOne
    private Airline airline;

    @ManyToOne
    private Port departurePort;

    @ManyToOne
    private Port arrivalPort;

    @Column(name = "departure_time")
    private LocalDateTime departureTime; 

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    /*should departureTime and arrivalTime include the date too 
    and be LocalDateTime, or is this meant to be just time so LocalTime, no date attached? */
}