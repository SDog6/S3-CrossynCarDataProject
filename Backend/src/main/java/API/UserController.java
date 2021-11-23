package API;
import Backend.Classes.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.connector.Response;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ch.qos.logback.core.status.Status;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*")
public class UserController {


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

}
