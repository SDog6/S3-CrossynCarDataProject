package Backend.Repo;

import Backend.Classes.Trip;
import Backend.Classes.TripEntry;
import Backend.DatabaseAccess.ITripDAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.core.query.*;

import java.time.ZonedDateTime;
import java.util.*;


@Repository
public class TripRepo implements ITripDAL
{
    @Autowired
    MongoTemplate mt;



    @Override
    public List<Trip> getAllTripsfromDB()
    {
        return mt.findAll(Trip.class, "Trips");
    }

    @Override
    public List<Trip> getAllTripswithoutTripEntriesfromDB()
    {
        Query q = new Query();
        q.fields().exclude("Entries");
        return mt.find(q, Trip.class, "Trips");
    }

    @Override
    public List<Trip> getAllTripswthoutTripEntriesfromDBwithOngoingStatus(boolean CurrentlyOngoing)
    {
        Query q = new Query();
        q.fields().exclude("Entries");
        return mt.find(q, Trip.class, "Trips");
    }


    @Override
    public void addTripinDB(Trip trip)
    {
        mt.save(trip);
    }

    @Override
    public Trip getTripbyIdinDB(String Id)
    {
        var id = Id;
        return mt.findById(id, Trip.class, "Trips");
    }

    @Override
    public Trip getTripbyVehicleIDinDB(String vehicleID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("vehicleId").is(vehicleID));
        return mt.findOne(query, Trip.class, "Trips");
    }

    @Override
    public Trip getOngoingTripbyVehicleIDinDB(String vehicleID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("vehicleId").is(vehicleID));
        query.addCriteria(Criteria.where("currentlyOngoing").is(true));
        return mt.findOne(query, Trip.class, "Trips");
    }

    @Override
    public void addEntryToActiveTripinDB(TripEntry entry, String VehicleID)
    {
        Query query= new Query();
        query.addCriteria(Criteria.where("vehicleId").is(VehicleID));
        query.addCriteria(Criteria.where("currentlyOngoing").is(true));

        Update update = new Update();
        update.addToSet("Entries", entry);

        mt.findAndModify(query, update, Trip.class, "Trips");
        System.out.println("Data Modified");
    }

    @Override
    public List<TripEntry> getLastThreeTripEntryFromTripinDB(String VehicleID)
    {
        Query query= new Query();
        query.addCriteria(Criteria.where("vehicleId").is(VehicleID));
        query.addCriteria(Criteria.where("currentlyOngoing").is(true));


        Trip details = mt.findOne(query, Trip.class, "Trips");

        Collections.sort(details.getEntries(), new Comparator<TripEntry>() {
            public int compare(TripEntry o1, TripEntry o2) {
                return o2.getDateTime().compareTo(o1.getDateTime());
            }
        });

        List<TripEntry> ties = new LinkedList<>();
        int i = 0;
        for (TripEntry Entry : details.getEntries())
        {
            i++;
            ties.add(Entry);
            if(i >= 3) { break; }
        }
        return ties;
    }

    @Override
    public void changeTripOngoingStatusinDB(boolean status, String tripID)
    {
        Query query= new Query();
        query.addCriteria(Criteria.where("_id").is("61978d31475e6315eb8ddd6c"));

        Update update = new Update();
        update.set("currentlyOngoing", status);
    }




}
