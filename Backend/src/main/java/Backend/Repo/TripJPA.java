package Backend.Repo;

import Backend.Classes.Trip;
import Backend.DatabaseAccess.ITripDAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public class TripJPA implements ITripDAL
{
    @Autowired
    ITripRepo repo;

    @Override
    public List<Trip> getTripByVehicleID(String name) {
        List<Trip> temp = null;
        temp = repo.getTripsByVehicleId(name);
        return temp;
    }

    @Override
    public List<Trip> getAllTrips() {
        return repo.findAll();
    }

    @Override
    public void addTripdb(Trip trip)
    {
    repo.save(trip);
    }
}
