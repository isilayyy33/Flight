package com.project.flight.service; //Declares that this class lives in the service package.

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.flight.exception.NoDataFoundException;
import com.project.flight.model.Passenger;
import com.project.flight.model.PassengerTypeEnum;
import com.project.flight.repository.PassengerRepository;

@Service
public class PassengerService {
/*@Service tells Spring: "this is a business-logic component, manage it for me." Spring automatically creates and manages an instance of this class so I never write new PassengerService() myself. */

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }
    /*This is constructor injection, when Spring creates PassengerService, it automatically finds an existing PassengerRepository instance and hands it in here. We mark the field final to guarantee it never changes after construction.*/

    public PassengerTypeEnum determinePassengerType(LocalDate birthDate) {

     if (birthDate == null) {
       throw new IllegalArgumentException("Error:Birth date is required to determine passenger type.");
}

int age = Period.between(birthDate, LocalDate.now()).getYears();
     
/*Period.between(birthDate, today) calculates the difference between two dates (in years, months, days). .getYears() grabs just the whole-year part — i.e., "how old is this person." */
/*every time this code runs, LocalDate.now() gives you whatever today's actual calendar date is */


        for (PassengerTypeEnum type : PassengerTypeEnum.values()) {
            if (age >= type.getMinAge() && age <= type.getMaxAge()) {
                return type;
            }

       
        }
        return PassengerTypeEnum.ADT;
    }
    /*PassengerTypeEnum.values() returns all the enum constants as an array — [ADT, CHD, INF]. The for loop checks each one: "does this calculated age fall within this type's min-max range?" It returns the first match. If somehow none matches (shouldn't normally happen), it falls back to ADT as a safe default. */

    // CREATE
    public Passenger savePassenger(Passenger passenger) {
        passenger.setPassengerType(determinePassengerType(passenger.getBirthDate()));
        return passengerRepository.save(passenger);
    }
/*Takes an incoming passenger object, calculates and sets its passengerType based on birth date, then saves it to the database via passengerRepository.save(). We return whatever save() gives back (now with its database-generated ID filled in). */


    // READ - hepsini getir
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }
/*findAll() is a ready-made method from JpaRepository — it returns every Passenger row in the database as a list. No extra logic needed here, just delegating straight to the repository. */


    // READ - id'ye göre tek bir kayıt getir
    public Passenger getPassengerById(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException("Passenger not found with id: " + id));
    }
/*findById(id) returns an Optional<Passenger> — because a record at that id might not exist. Optional is a wrapper type meaning "there's either a value here, or there isn't"; it's a safer approach than just returning null.

.orElseThrow(() -> new RuntimeException(...)) says: "if there's a value inside, give it to me; otherwise, throw this error." So instead of silently returning null for a bad id, we throw a clear error — which makes debugging much easier since you immediately know something went wrong and why. */

// UPDATE
    public Passenger updatePassenger(Long id, Passenger updatedPassenger) {
        Passenger existing = getPassengerById(id);

        existing.setFirstName(updatedPassenger.getFirstName());
        existing.setLastName(updatedPassenger.getLastName());
        existing.setPassportNumber(updatedPassenger.getPassportNumber());
        existing.setEmail(updatedPassenger.getEmail());
        existing.setBirthDate(updatedPassenger.getBirthDate());
        existing.setPassengerType(determinePassengerType(updatedPassenger.getBirthDate()));

        return passengerRepository.save(existing);
    }

/*First, we fetch the existing record from the database (reusing the getPassengerById method above, to avoid duplicating that lookup logic). Then we update that existing object's fields one by one with the new incoming data (updatedPassenger). We also recalculate passengerType in case the birth date changed. Finally, we call save() — in JPA, calling save() on an object whose id already exists in the database means update, not insert a new row.

Why update existing instead of just saving updatedPassenger directly? Because updatedPassenger's id might be null or missing (it's incoming data, not yet a database record) — using existing guarantees we're updating the correct row with the correct id. */

    // DELETE
    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
    }

/*deleteById() is another ready-made method — it removes the row with that id from the database. Return type is void because there's nothing meaningful to return after a delete. */

    // Single source of truth for "find Passenger by id or throw" —
    // used by getPassengerById, and other services (like TicketService) that need to attach a real Passenger entity.
    public Passenger getPassengerEntityById(Long id) {
    return passengerRepository.findById(id)
            .orElseThrow(() -> new NoDataFoundException("Passenger not found with id: " + id));
    }
}