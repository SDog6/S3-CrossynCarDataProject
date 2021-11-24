package API;

import Backend.Classes.Trip;
import Backend.DatabaseAccess.ITripDAL;
import Backend.DatabaseAccess.ITripService;
import Backend.Interfaces.ITripContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/Trips")

public class TripController {

    @Autowired
    ITripContainer dal;

    @GetMapping
    public ResponseEntity<List<Trip>> getAllTrips(@RequestBody String username){
        List<Trip> test = null;

        test = dal.dbgetAllTrips();

        if(test != null){
            return ResponseEntity.ok().body(test);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

}
