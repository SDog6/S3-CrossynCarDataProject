package API;

import Backend.Classes.User;
import Backend.Classes.Vehicle;
import Backend.Interfaces.IUser;
import Backend.Interfaces.IVehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/Vehicle")
public class VehicleController {

    @Autowired
    IVehicleRepo repo;
    @Autowired
    IUser Urepo;


    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok().body(repo.findAll());
    }


    @GetMapping("/UserVehicles/{username}")
    public ResponseEntity<List<Vehicle>> getAllVehicles(@PathVariable(value = "username") String username) {
        User u = Urepo.getSingleUserByUsername(username);
        List<Vehicle> connectedV = new ArrayList<>();
        for (String id: u.getConnectedVehicles()) {
            Vehicle v = repo.getVehicleById(id);
            connectedV.add(v);
        }
        return ResponseEntity.ok().body(connectedV);
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
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehiclePath(@PathVariable(value = "id") String vehicleID) {
        Vehicle temp = repo.getVehicleById(vehicleID);
        return ResponseEntity.ok().body(temp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicleDetails(@PathVariable(value="id") String vehicleID,@RequestBody Vehicle vehicle){
        Vehicle v = repo.getVehicleById(vehicleID);
        v.setLplate(vehicle.getLplate());
        v.setColor(vehicle.getColor());
        Vehicle update = repo.save(vehicle);
        return ResponseEntity.ok().body(update);
    }


    @PutMapping("/DisableVehicle/{id}")
    public ResponseEntity<List<Vehicle>> ChangeVehicleStatus(@PathVariable(value="id") String vehicleID){
        Vehicle v = repo.getVehicleById(vehicleID);
        if (v.isActive()){
            v.setActive(false);
        }
        else{
            v.setActive(true);
        }
        repo.save(v);
        return ResponseEntity.ok().body(repo.findAll());
    }

}
