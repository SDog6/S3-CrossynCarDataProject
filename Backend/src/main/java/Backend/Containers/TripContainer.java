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

    private List<Trip>  trips;

    public TripContainer()
    {
        this.trips = new ArrayList<>();
    }

    public List<Trip> GetAllTrips(){
        return dal.getAllTripsfromDB();
    }


    //Functions (Crud)

    /*
    public boolean AddTrip(Trip t)
    {
        if(trips.add(t))
        {
            return true;
        }
        return false;
    }*/

    public boolean AddTrip(Trip t)
    {
        if(trips.add(t))
        {

            return true;
        }
        return false;
    }

    public void dbSaveTrip(Trip t)
    {
        dal.addTripinDB(t);
    }
    public Trip dbGetTrip(String id) {return dal.getTripbyIdinDB(id);}
    public List<TripEntry> dbGetLast3TripEntriesFromOngoingTripWithVehicleID(String VehID) {return dal.getLastThreeTripEntryFromTripinDB(VehID);};
    public List<Trip> dbgetAllTrips() {return dal.getAllTripsfromDB();}
    public Trip dbGetOngoingTripbyVehicleID(String vehicleID) {return dal.getOngoingTripbyVehicleIDinDB(vehicleID);}
    public List<Trip> dbFetchAllTripSummaries() {return dal.getAllTripswithoutTripEntriesfromDB();}
    public List<Trip> dbFetchAllTripSummarieswithStatus(boolean isActive) {return dal.getAllTripswthoutTripEntriesfromDBwithOngoingStatus(isActive);}
    public void dbSaveEntriestoActiveTripwithVehicleID(List<TripEntry> entries, String VehicleID) {dal.addTripEntryListToActiveTripinDBwithVehicleID(entries, VehicleID);}
    public void dbSetActiveTripOngoingStatusToFalsewithVehicleID(String VehicleID) {dal.setTripStatustoFalseinDBwithVehicleID(VehicleID);};

    public Trip CreateTrip(String vehicleId, ZonedDateTime startTime, ZonedDateTime endTime, boolean currentlyOngoing)
    {
         return new Trip(vehicleId, startTime, endTime, currentlyOngoing);
    }
    //TODO: make this an easy create, but also add it too Trip class itself
    /*public Trip CreateTrip(String vehicleId, LocalDate startTime){
        return new Trip(vehicleId, startTime, );
    }*/

    public List<Trip> ReadTrips()
    {
        return trips;
    }

    public boolean UpdateTrips(Trip oldTrip, Trip newTrip) //replace 1 item
    {
        try
        {
            trips.remove(oldTrip);

            try //if can't add new trip re-add old trip
            {
                trips.add(newTrip);
            }
            catch(Exception ex)
            {
                trips.add(oldTrip);
                return false;
            }

            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }

    public boolean DeleteTrip(Trip trip)
    {
        try
        {
            trips.remove(trip);
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }

    public boolean VehicleOnTrip(String vehicleID)
    {
        for(Trip trippu: trips )
        {
            if(trippu.getVehicleId().equals(vehicleID) && trippu.isCurrentlyOngoing())
            {
                return true;
            }
        }
        return false;
    }

    public Trip GetOngoingTripFromVehicleID(String vehicleID)
    {
        for(Trip trippu: trips )
        {
            if(trippu.getVehicleId().equals(vehicleID) && trippu.isCurrentlyOngoing())
            {
                return trippu;
            }
        }
        return null;
    }

    public List<Trip> GetPastTripsFromVehicleID(String vehicleID)
    {
        List<Trip> pastTrips=new LinkedList<Trip>();

        for( Trip trippu : trips)
        {
            if(trippu.getVehicleId().equals(vehicleID))
            {
                pastTrips.add(trippu);
            }
        }
        if(!pastTrips.isEmpty())
        {
            return pastTrips;
        }
        else
        {
            return null;
        }
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
                    for(TripEntry te : trippu.getEntries())
                    {
                        if(trippu.getEntries().indexOf(te) != 0 && trippu.getEntries().indexOf(te) != 1 && trippu.getEntries().indexOf(te) != 2 )
                        {
                            temp.add(te);
                            //System.out.println(te);
                        }

                    } //HACK: this might work...

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
