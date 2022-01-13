package Backend.Containers;

import Backend.Classes.*;
import Backend.Interfaces.DatabaseAccess.ITripDAL;
import Backend.Interfaces.ITripContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class TripContainer implements ITripContainer {
    //Items

    @Autowired
    ITripDAL dal;

    private List<Trip> trips;

    public TripContainer() {
        this.trips = new ArrayList<>();
    }

    public List<Trip> GetAllTrips() {
        return dal.getAllTripsfromDB();
    }


    public boolean AddTrip(Trip t) {
        if (trips.add(t)) {

            return true;
        }
        return false;
    }

    public void dbSaveTrip(Trip t) {
        dal.addTripinDB(t);
    }



    public Trip dbGetTrip(String id) {return dal.getTripbyIdinDB(id);}
    public List<TripEntry> dbGetLast3TripEntriesfromOngoingTripWithVehicleID(String VehID) {return dal.getLastThreeTripEntriesFromTripinDBwithVehicleID(VehID);};
    public ArrayList<TripEntry> dbGetLast3TripEntriesfromTripWithID(String ID) {return dal.getLastThreeTripEntriesFromTripinDBwithID(ID);};
    public void dbRemoveLast3TripEntriesfromTripWithID(String ID) {dal.rmLastThreeTripEntriesFromTripinDBwithID(ID);};
    public List<Trip> dbgetAllTrips() {return dal.getAllTripsfromDB();}
    public Trip dbGetOngoingTripbyVehicleID(String vehicleID) {return dal.getOngoingTripbyVehicleIDinDB(vehicleID);}
    public List<Trip> dbFetchAllTripSummaries() {return dal.getAllTripswithoutTripEntriesfromDB();}
    public List<Trip> dbFetchAllTripSummarieswithStatus(boolean isActive) {return dal.getAllTripswthoutTripEntriesfromDBwithOngoingStatus(isActive);}
    public void dbSaveEntriestoActiveTripwithVehicleID(List<TripEntry> entries, String VehicleID) {dal.addTripEntryListToActiveTripinDBwithVehicleID(entries, VehicleID);}

    public void dbSetActiveTripOngoingStatusToFalsewithVehicleID(String VehicleID) {dal.setTripStatustoFalseinDBwithVehicleID(VehicleID);};
    public void dbSetActiveTripEndTimewithVehicleID(String vehicleID, ZonedDateTime endTime) {dal.setOngoingTripEndTimeinDBwithVehicleID(vehicleID, endTime);};
    public void dbSetActiveTripEndAddresswithVehicleID(String vehicleID, String EndAddress) {dal.setOngoingTripEndAddressinDBwithVehicleID(vehicleID, EndAddress);}

    public void dbDeleteTripEntriesfromTrip(String tripID, List<TripEntry> entries) {dal.removeTripEntriesfromTripinDBwithID(tripID, entries);}

    public void LoadTrips()
    {
        trips = dbFetchAllTripSummarieswithStatus(true);
        for(Trip trip : trips)
        {
            trip.setEntries(dbGetLast3TripEntriesfromTripWithID(trip.getid()));
            dbRemoveLast3TripEntriesfromTripWithID(trip.getid());
            //dbDeleteTripEntriesfromTrip(trip.getid(), trip.getEntries()); //might work too fast, should work in theorie, if problems arise, uncomment for loop under here;
        }
    }
    public Trip CreateTrip(String vehicleId, ZonedDateTime startTime, ZonedDateTime endTime, boolean currentlyOngoing) {
        return new Trip(vehicleId, startTime, endTime, currentlyOngoing);
    }
    //TODO: make this an easy create, but also add it too Trip class itself
    /*public Trip CreateTrip(String vehicleId, LocalDate startTime){
        return new Trip(vehicleId, startTime, );
    }*/

    public List<Trip> ReadTrips() {
        return trips;
    }

    public boolean UpdateTrips(Trip oldTrip, Trip newTrip) //replace 1 item
    {
        try {
            trips.remove(oldTrip);

            try //if can't add new trip re-add old trip
            {
                trips.add(newTrip);
            } catch (Exception ex) {
                trips.add(oldTrip);
                return false;
            }

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean DeleteTrip(Trip trip) {
        try {
            trips.remove(trip);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean VehicleOnTrip(String vehicleID) {
        for (Trip trippu : trips) {
            if (trippu.getVehicleId().equals(vehicleID) && trippu.isCurrentlyOngoing()) {
                return true;
            }
        }
        return false;
    }

    public Trip GetOngoingTripFromVehicleID(String vehicleID) {
        for (Trip trippu : trips) {
            if (trippu.getVehicleId().equals(vehicleID) && trippu.isCurrentlyOngoing()) {
                return trippu;
            }
        }
        return null;
    }

    public List<Trip> GetPastTripsFromVehicleID(String vehicleID) {
        List<Trip> pastTrips = new LinkedList<Trip>();

        for (Trip trippu : trips) {
            if (trippu.getVehicleId().equals(vehicleID)) {
                pastTrips.add(trippu);
            }
        }
        if (!pastTrips.isEmpty()) {
            return pastTrips;
        } else {
            return null;
        }
    }
    public void FinishUpTrip(Trip trip)
    {

        //find trip
        Trip toBeEnded = trips.get(trips.indexOf(trip));
        //push latest non fake entries to db
        dbSaveEntriestoActiveTripwithVehicleID(trip.getEntriesWithoutFake(), trip.getVehicleId());

        //push end time and set status to false db
        dbSetActiveTripEndTimewithVehicleID(trip.getVehicleId(),trip.GetLatestTripEntry().getDateTime());
        dbSetActiveTripEndAddresswithVehicleID(trip.getVehicleId(), trip.getEndAddress());
        dbSetActiveTripOngoingStatusToFalsewithVehicleID(trip.getVehicleId());
        //remove from memory
        trips.remove(trip);
    }

    public boolean AddToTripWithVehicleID(String vehicleID, TripEntry tripEntry)
    {
        for(Trip trippu: trips )
        {
            if(trippu.getVehicleId().equals(vehicleID) && trippu.isCurrentlyOngoing())
            {
                trippu.AddTripEntry(tripEntry);
                if(trippu.getEntries().size() >= 10)
                {
                    List<TripEntry> temp = new ArrayList<TripEntry>();
                    for(int i = trippu.getEntries().size() -1; i >= 0; i--)//is reversed so it's kinda correct in the db
                    {
                        TripEntry te = trippu.getEntries().get(i);
                        if(trippu.getEntries().indexOf(te) != 0 && trippu.getEntries().indexOf(te) != 1 && trippu.getEntries().indexOf(te) != 2 )
                        {
                            if (te.getFake() != true) //check if the entry is a fake or not
                            {
                                temp.add(te);
                                //System.out.println(te);
                            }
                        }

                    } //HACK: this might work...

                    System.out.println(temp.toString());
                    dbSaveEntriestoActiveTripwithVehicleID(temp, vehicleID);
                    for(TripEntry entry : temp)
                    {
                        trippu.RemoveTripEntry(entry);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
