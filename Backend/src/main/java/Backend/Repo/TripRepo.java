package Backend.Repo;

import Backend.Classes.Trip;
import Backend.Classes.TripEntry;
import Backend.DatabaseAccess.ITripDAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.core.query.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;


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
    public List<Trip> getLastThreeTripEntryFromTripinDB(String VehicleID) {
        return null;
    }

    @Override
    public void changeTripOngoingStatusinDB(boolean status) {

    }


}
