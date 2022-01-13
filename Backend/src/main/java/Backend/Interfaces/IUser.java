package Backend.Interfaces;

import Backend.Classes.User;
import Backend.Classes.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

public interface IUser extends MongoRepository<User, String> {
    Optional<User> getUserByUsername(String username);
    User getSingleUserByUsername(String username);
    String getUserByUsernameAndAndConnectedVehicles(String username,String VehicleID);
    User getUserByConnectedVehiclesAndRole(String connectedVehicles,String role);
}
