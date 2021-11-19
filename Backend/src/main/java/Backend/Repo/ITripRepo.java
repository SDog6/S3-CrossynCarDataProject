package Backend.Repo;

import Backend.Classes.Trip;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ITripRepo extends MongoRepository<Trip, Integer>
{

}
