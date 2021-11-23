package Backend.DatabaseAccess;

import Backend.Classes.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService implements ITripService{

    ITripDAL dal;

    @Autowired
    public TripService(ITripDAL dal)
    {
        this.dal =dal;
    }

    @Override
    public List<Trip> getTripByVehicleID(String name) {
//        return dal.getTripByVehicleID(name);
        return null;
    }

    @Override
    public List<Trip> getAllTrips() {
        return dal.getAllTripsfromDB();
    }

    @Override
    public void addTripdb(Trip trip) {
        dal.addTripinDB(trip);
    }
}
