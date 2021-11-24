package Backend.Interfaces;

import Backend.Classes.Trip;

import java.util.List;

public interface ITripContainer
{
    void dbSaveTrip(Trip t);
    Trip dbGetOngoingTripbyVehicleID(String vehicleID);
    List<Trip> dbgetAllTrips();
    List<Trip> dbFetchAllTripSummaries();
    List<Trip> dbFetchAllTripSummarieswithStatus(boolean isActive);
}
