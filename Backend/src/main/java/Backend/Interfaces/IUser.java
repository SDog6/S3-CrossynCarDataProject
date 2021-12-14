package Backend.Interfaces;

import Backend.Classes.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.lang.reflect.Member;
import java.util.Optional;

public interface IUser extends MongoRepository<User, String> {
    Optional<User> getUserByUsername(String username);
    String getUserByUsernameAndAndConnectedVehicles(String username,String VehicleID);
}