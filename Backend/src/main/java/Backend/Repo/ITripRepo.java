package Backend.Repo;

import Backend.Classes.Trip;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ITripRepo extends MongoRepository<Trip, Integer>
{
    List<Trip> getTripsByVehicleId(String vehicleId);
}
