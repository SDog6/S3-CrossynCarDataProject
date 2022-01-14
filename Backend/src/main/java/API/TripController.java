package API;

import Backend.Classes.Trip;
import Backend.Classes.User;
import Backend.Classes.Vehicle;
import Backend.Interfaces.DatabaseAccess.ITrip;
import Backend.Interfaces.ITripContainer;
import Backend.Interfaces.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/Trips")
@Service

public class TripController {

    @Autowired
    ITripContainer dal;

    @Autowired
    ITrip repo;

    @Autowired
    IUser Urepo;

    @GetMapping
    public ResponseEntity<List<Trip>> getAllTrips(){
        List<Trip> test = null;
        List<Trip> temp = new ArrayList<>();

        //test = dal.dbgetAllTrips();
        test = repo.findAll();

        
        if(test != null){
            return ResponseEntity.ok().body(test);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/connected/{username}")
    public ResponseEntity<List<Trip>> getAllConnectedTrips(@PathVariable(value = "username") String username){
        User u = Urepo.getSingleUserByUsername(username);
        List<Trip> connectedT = new ArrayList<>();
        for (String id: u.getConnectedTrips()) {
            Trip temp = dal.dbGetTrip(id);
            connectedT.add(temp);
        }
        return ResponseEntity.ok().body(connectedT);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Trip> getCByName(@PathVariable(value = "id") String id) {
        Trip trip = dal.dbGetTrip(id);
        if (trip != null) {
            return ResponseEntity.ok().body(trip);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
