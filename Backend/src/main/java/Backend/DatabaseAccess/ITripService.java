package Backend.DatabaseAccess;

import Backend.Classes.Trip;

import java.util.List;

public interface ITripService {
    List<Trip> getTripByVehicleID (String name);
    List<Trip> getAllTrips();
    void addTripdb(Trip trip);
}
