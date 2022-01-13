package Backend.Interfaces.DatabaseAccess;

import Backend.Classes.Trip;
import Backend.Classes.TripEntry;

import java.time.ZonedDateTime;
import java.util.ArrayList;
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

        List<TripEntry> getLastThreeTripEntriesFromTripinDBwithVehicleID(String VehicleID);
        ArrayList<TripEntry> getLastThreeTripEntriesFromTripinDBwithID(String ID);

        void addTripinDB(Trip trip);

        void removeTripEntryfromTripinDBwithID(String tripID, TripEntry entry);
        void removeTripEntriesfromTripinDBwithID(String tripID, List<TripEntry> entries);


        void setOngoingTripSpeedLimitBreakinDBwithVehicleID(String vehicleID, int speedbreak);
        void addTripEntryToActiveTripinDB(TripEntry entry, String VehicleID);
        void addTripEntryListToActiveTripinDBwithVehicleID(List<TripEntry> Entries, String VehicleID);
        void changeTripOngoingStatusinDB(boolean status, String tripID);
        void setTripStatustoFalseinDBwithVehicleID(String VehicleID);
        void setOngoingTripEndTimeinDBwithVehicleID(String vehicleID, ZonedDateTime dateTime);
        void setOngoingTripEndAddressinDBwithVehicleID(String vehicleID, String string);
        void setOngoingTripAverageSpeedinDBwithVehicleID(String vehicleID, int speed);
        void setOngoingTripAverageRoadinDBwithVehicleID(String vehicleID, int road);
        void setOngoingTripEndDriverinDBwithVehicleID(String vehicleID, String string);

        void setTripEndTimeinDBwithTripID(String tripID, ZonedDateTime dateTime);

        void rmLastThreeTripEntriesFromTripinDBwithID(String ID);
}
