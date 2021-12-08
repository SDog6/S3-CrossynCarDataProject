package API;
import Backend.Classes.User;
import Backend.Classes.Vehicle;
import Backend.Interfaces.IUser;
import Backend.Interfaces.IVehicleRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.connector.Response;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ch.qos.logback.core.status.Status;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    IUser repo;



    private static final FakeUserData data = new FakeUserData();

        @PostMapping("/login")
        public ResponseEntity loginUser(@RequestBody User user){
            System.out.println(user.getPassword());
            System.out.println(user.getUsername());

            for (User other : data.GetAllUsers()) {
                if (other.getUsername().equals(user.getUsername()) & other.getPassword().equals(user.getPassword())) {
                    return new ResponseEntity(HttpStatus.OK);
                }
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        }

        @PostMapping("/logout")
        public boolean logUserOut(@RequestBody User user) {
            for (User other : data.GetAllUsers()) {
                if (other.equals(user)) {
                    return true;
                }
            }
            return false;
        }

    @PostMapping("/User")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (repo.getUserByUsername(user.getUsername()) != null){
            String entity =  "This user already exists.";
            return new ResponseEntity(entity, HttpStatus.CONFLICT);
        } else {
            repo.save(user);
            String url = "vehicle" + "/" + user.getUsername(    );
            URI uri = URI.create(url);
            return new ResponseEntity(uri,HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/User", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(repo.findAll());
    }

    @PutMapping("/User/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User user){
        User u = repo.getUserByUsername(username);
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        User update = repo.save(u);
        return ResponseEntity.ok().body(update);

    }


}
