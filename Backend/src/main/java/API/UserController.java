package API;
import API.Security.UserCreateRequest;
import Backend.Classes.User;
import Backend.Classes.Vehicle;
import Backend.Containers.UserService;
import Backend.Interfaces.IUser;
import Backend.Interfaces.IUserContainer;
import Backend.Interfaces.IVehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    IUser repo;

    @Autowired
    IVehicleRepo Vrepo;

    @Autowired
    IUserContainer dal;

    @Autowired
    private UserService userService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity createUser(@RequestBody UserCreateRequest userCreateRequest) {
        userService.createUser(userCreateRequest);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/User", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(repo.findAll());
    }

    @PutMapping("/User/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User user){
        User u = dal.getUserfromDBbyUserName(username);
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        dal.addUserinDB(u);
        return ResponseEntity.ok().body(u);}
    @PutMapping("/{username}/{vehicleID}")
    public ResponseEntity<String> updateConnectedVehicle(@PathVariable String username, @PathVariable String vehicleID){
        User u = userService.readUserByUsername(username);
        List<String> temp = u.getConnectedVehicles();
        Vehicle check = Vrepo.getVehicleById(vehicleID);
        if(check != null && u != null){

            if (u.getConnectedVehicles().contains(check.getId())) {
                temp.remove(vehicleID);
                u.setConnectedVehicles(temp);
                dal.addUserinDB(u);
                return ResponseEntity.ok().body("Disconnected vehicle and user");
            }
            if (check.isActive()) {
                if (u.getRole().equals("DRIVER") && repo.getUserByConnectedVehiclesAndRole(vehicleID,"DRIVER")  != null){
                    return ResponseEntity.ok().body("Only 1 Vehicle can be connected to a driver at a time");
                }
                if (u.getRole().equals("FLEETOWNER") && repo.getUserByConnectedVehiclesAndRole(vehicleID,"FLEETOWNER")  != null){
                    return ResponseEntity.ok().body("Vehicle can only have 1 fleet owner at a time");
                }
                temp.add(vehicleID);
                u.setConnectedVehicles(temp);
                dal.addUserinDB(u);
                return ResponseEntity.ok().body("Connected");
            } else {
                return ResponseEntity.ok().body("Vehicle is disabled please choose another one");
            }

        }
        else{
            return ResponseEntity.ok().body("Select valid vehicle and user");
        }


    }


}
