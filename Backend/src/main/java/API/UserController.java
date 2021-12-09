package API;
import API.Security.UserCreateRequest;
import Backend.Classes.User;
import Backend.Classes.Vehicle;
import Backend.Interfaces.IUser;
import Backend.Interfaces.IUserContainer;
import Backend.Interfaces.IVehicleRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.connector.Response;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ch.qos.logback.core.status.Status;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    IUser repo;

    @Autowired
    IUserContainer dal;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @PostMapping("/User")
    public ResponseEntity createUser(@RequestBody UserCreateRequest userCreateRequest) {
        User user = new User();
        Optional<User> byUsername = Optional.ofNullable(dal.getUserfromDBbyUserName(userCreateRequest.getUsername()));
        if (byUsername.isPresent()) {
            throw new RuntimeException("User already registered. Please use different username.");
        }
        user.setUsername(userCreateRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        user.setRole("DRIVER");
        repo.save(user);
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


}
