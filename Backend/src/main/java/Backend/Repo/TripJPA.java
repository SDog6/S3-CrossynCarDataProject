package Backend.Repo;

import Backend.Classes.Trip;
import Backend.DatabaseAccess.ITripDAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TripJPA implements ITripDAL
{
    @Autowired
    ITripRepo repo;

    @Override
    public Trip getTripByName(String name) {
        return null;
    }

    @Override
    public List<Trip> getAllTrips() {
        return null;
    }

    @Override
    public void addTripdb(Trip trip)
    {
    repo.save(trip);
    }
}
