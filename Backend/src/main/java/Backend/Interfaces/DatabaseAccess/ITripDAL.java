package Backend.Interfaces.DatabaseAccess;

import Backend.Classes.Trip;
import Backend.Classes.TripEntry;

import java.util.List;
import java.util.Optional;

public interface ITripDAL
{

        List<Trip> getAllTripsfromDB();
        List<Trip> getAllTripswithoutTripEntriesfromDB();
        List<Trip> getAllTripswthoutTripEntriesfromDBwithOngoingStatus(boolean CurrentlyOngoing);
        Trip getTripbyIdinDB(String ID);
        Trip getTripbyVehicleIDinDB(String vehicleID);

        Trip getOngoingTripbyVehicleIDinDB(String vehicleID);

        List<TripEntry> getLastThreeTripEntryFromTripinDB(String VehicleID);


        void addTripinDB(Trip trip);


        void addTripEntryToActiveTripinDB(TripEntry entry, String VehicleID);
        void addTripEntryListToActiveTripinDBwithVehicleID(List<TripEntry> Entries, String VehicleID);
        void changeTripOngoingStatusinDB(boolean status, String tripID);
        void setTripStatustoFalseinDBwithVehicleID(String VehicleID);

}
