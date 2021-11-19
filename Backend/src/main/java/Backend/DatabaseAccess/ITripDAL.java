package Backend.DatabaseAccess;

import Backend.Classes.Trip;

import java.util.List;

public interface ITripDAL
{

        Trip getTripByName (String name);
        List<Trip> getAllTrips();
        void addTripdb(Trip trip);

}
