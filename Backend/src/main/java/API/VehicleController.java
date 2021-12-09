package API;

import Backend.Classes.Vehicle;
import Backend.Interfaces.IVehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/Vehicle")
public class VehicleController {

    @Autowired
    IVehicleRepo repo;

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok().body(repo.findAll());
    }

    @PostMapping()
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
        if (repo.getVehicleByLplate(vehicle.getLplate()) != null){
            if(repo.getVehicleById(vehicle.getId()) != null){
                String entity =  "This vehicle already exists.";
                return new ResponseEntity(entity, HttpStatus.CONFLICT);
            }
        } else {
            repo.save(vehicle);
            String url = "vehicle" + "/" + vehicle.getBrand();
            URI uri = URI.create(url);
            return new ResponseEntity(uri,HttpStatus.CREATED);
        }
        return null;
    }


}
