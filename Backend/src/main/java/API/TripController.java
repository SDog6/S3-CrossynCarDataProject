package API;

import Backend.Classes.Trip;
import Backend.DatabaseAccess.ITripDAL;
import Backend.DatabaseAccess.ITripService;
import Backend.Repo.TripJPA;
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
    ITripService dal;

    @GetMapping
    public ResponseEntity<List<Trip>> getAllTrips(@RequestBody String username){
        List<Trip> test = null;

        test = dal.getAllTrips();

        if(test != null){
            return ResponseEntity.ok().body(test);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

}
