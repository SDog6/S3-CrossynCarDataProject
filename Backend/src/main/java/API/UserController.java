package API;
import API.Security.UserCreateRequest;
import Backend.Classes.User;
import Backend.Containers.UserService;
import Backend.Interfaces.IUser;
import Backend.Interfaces.IUserContainer;
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
        return ResponseEntity.ok().body(u);

    }

    @PutMapping("/{username}/{vehicleID}")
    public ResponseEntity<String> updateConnectedVehicle(@PathVariable String username, @PathVariable String vehicleID){
        User u = userService.readUserByUsername(username);
        List<String> temp = u.getConnectedVehicles();
        temp.add(vehicleID);
        u.setConnectedTrips(temp);
        dal.addUserinDB(u);
        return ResponseEntity.ok().body("Added");

    }


}
