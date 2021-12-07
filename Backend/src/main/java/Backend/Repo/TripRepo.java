package Backend.Repo;

import Backend.Classes.Trip;
import Backend.Classes.TripEntry;
import Backend.Interfaces.DatabaseAccess.ITripDAL;
import com.mongodb.client.model.*;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ExecutableUpdateOperation;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.core.query.*;

import java.time.ZonedDateTime;
import java.util.*;

import static com.mongodb.client.model.Updates.pushEach;


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
        q.addCriteria(Criteria.where("currentlyOngoing").is(CurrentlyOngoing));
        q.fields().exclude("Entries");
        return mt.find(q, Trip.class, "Trips");
    }


    @Override
    public void addTripinDB(Trip trip)
    {
        mt.save(trip);
    }

    @Override
    public void removeTripEntryfromTripinDBwithID(String tripID, TripEntry entry)
    {
        Query q = new Query();
        q.addCriteria(Criteria.where("_id").is(tripID));

        Update update = new Update();
        update.pull("Entries", entry);

        mt.findAndModify(q, update, Trip.class, "Trips");

    }

    @Override
    public void removeTripEntriesfromTripinDBwithID(String tripID, List<TripEntry> entries)
    {
        Query q = new Query();
        q.addCriteria(Criteria.where("_id").is(tripID));

        Update update = new Update();
        update.pullAll("Entries", new List[]{entries});

        mt.findAndModify(q, update, Trip.class, "Trips");
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
    public void addTripEntryToActiveTripinDB(TripEntry entry, String VehicleID)
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
    public void addTripEntryListToActiveTripinDBwithVehicleID(List<TripEntry> Entries, String VehicleID) {
        Query query= new Query();
        query.addCriteria(Criteria.where("vehicleId").is(VehicleID));
        query.addCriteria(Criteria.where("currentlyOngoing").is(true));

        Update update = new Update();
//        for(TripEntry entry : Entries)
//        {
//
//            update.addToSet("Entries", entry);
//        }
        update.push("Entries").each(Entries);
//        final Bson BsonTest = pushEach("Entries", Entries);
//        mt.findAndModify(query, BsonTest, Trip.class, "Trips");

        mt.findAndModify(query, update, Trip.class, "Trips");


    }

    @Override
    public List<TripEntry> getLastThreeTripEntriesFromTripinDBwithVehicleID(String VehicleID)
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
    public ArrayList<TripEntry> getLastThreeTripEntriesFromTripinDBwithID(String ID) {
        Query query= new Query();
        query.addCriteria(Criteria.where("_id").is(ID));


        Trip details = mt.findOne(query, Trip.class, "Trips");

        Collections.sort(details.getEntries(), new Comparator<TripEntry>() {
            public int compare(TripEntry o1, TripEntry o2) {
                return o2.getDateTime().compareTo(o1.getDateTime());
            }
        });

        ArrayList<TripEntry> ties = new ArrayList<>();
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
        query.addCriteria(Criteria.where("_id").is(tripID));

        Update update = new Update();
        update.set("currentlyOngoing", status);

        mt.findAndModify(query, update, Trip.class, "Trips");
    }

    @Override
    public void setTripStatustoFalseinDBwithVehicleID(String VehicleID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("vehicleId").is(VehicleID));
        query.addCriteria(Criteria.where("currentlyOngoing").is(true));

        Update update = new Update();
        update.set("currentlyOngoing", false);

        mt.findAndModify(query, update, Trip.class, "Trips");

    }

    @Override
    public void setOngoingTripEndTimeinDBwithVehicleID(String vehicleID, ZonedDateTime dateTime)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("vehicleId").is(vehicleID));
        query.addCriteria(Criteria.where("currentlyOngoing").is(true));

        Update update = new Update();
        update.set("endTime", dateTime);

        mt.findAndModify(query, update, Trip.class, "Trips");
    }

    @Override
    public void setTripEndTimeinDBwithTripID(String tripID, ZonedDateTime dateTime)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(tripID));

        Update update = new Update();
        update.set("endTime", dateTime);

        mt.findAndModify(query, update, Trip.class, "Trips");

    }


}
