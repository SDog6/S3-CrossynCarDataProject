package Backend.Interfaces.DatabaseAccess;

import Backend.Classes.Trip;
import Backend.Classes.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ITrip extends MongoRepository<Trip, String> {
    List<Trip> getTripsByDriver(String Driver);
    Trip getTripById(String id);
}
