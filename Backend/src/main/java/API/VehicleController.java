package API;

import Backend.Classes.Vehicle;
import Backend.Containers.VehicleContainer;
import Backend.Interfaces.IVehicleContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/Vehicle")


public class VehicleController {

    @Autowired
    IVehicleContainer repo;

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok().body(repo.dbGetAllVehicles());
    }

    @PostMapping()
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
        if (repo.dbGetVehicleByLicenseplate(vehicle.getLicensePlate()) != null){
            String entity =  "This vehicle already exists.";
            return new ResponseEntity(entity, HttpStatus.CONFLICT);
        } else {
            repo.dbSaveVehicle(vehicle);
            String url = "vehicle" + "/" + vehicle.getVehicleID();
            URI uri = URI.create(url);
            return new ResponseEntity(uri,HttpStatus.CREATED);
        }
    }

}
