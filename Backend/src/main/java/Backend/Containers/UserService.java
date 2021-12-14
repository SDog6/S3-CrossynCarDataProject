package Backend.Containers;

import API.Security.UserCreateRequest;
import Backend.Classes.User;
import Backend.Interfaces.IUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUser userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User readUserByUsername (String username) {
        return userRepository.getUserByUsername(username).orElseThrow(EntityNotFoundException::new);
    }
    public void createUser(UserCreateRequest userCreateRequest) {
        User user = new User();
        Optional<User> byUsername = userRepository.getUserByUsername(userCreateRequest.getUsername());
        if (byUsername.isPresent()) {
            throw new RuntimeException("User already registered. Please use different username.");
        }
        user.setUsername(userCreateRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        user.setRole(userCreateRequest.getRole());
        List<String> connectedTrips = new ArrayList<>();
        List<String> connectedVehicles = new ArrayList<>();
        user.setConnectedTrips(connectedTrips);
        user.setConnectedVehicles(connectedVehicles);
        userRepository.save(user);
    }

    public String getVehicleID(String username,String VehicleID){
        return userRepository.getUserByUsernameAndAndConnectedVehicles(username,VehicleID);
    }
}
