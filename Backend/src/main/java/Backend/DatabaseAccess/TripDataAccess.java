package Backend.DatabaseAccess;

import Backend.Classes.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TripDataAccess implements ITripDAL{


    @Autowired
    ITripRepo repo;

    @Override
    public Trip getTripByName(String name) {
        return repo.GetTripByName(name);
    }

    @Override
    public List<Trip> getAllTrips() {
        return repo.findAll();
    }

    @Override
    public void addTrip(Trip country) {
        repo.save(country);
    }
}
