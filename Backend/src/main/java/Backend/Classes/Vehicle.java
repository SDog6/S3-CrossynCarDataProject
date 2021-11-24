package Backend.Classes;

import Backend.Interfaces.IVehicleContainer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Document(collection = "Vehicle")
public class Vehicle
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String vehicleID;

    private String brand;

    private String licensePlate;

    private String color;

    private boolean currentlyInUse;

    public Vehicle(String vehicleID, String brand, String licensePlate, String color,boolean currentlyInUse) {
        this.vehicleID = vehicleID;
        this.brand = brand;
        this.licensePlate = licensePlate;
        this.color = color;
        this.currentlyInUse = currentlyInUse;
    }


}
