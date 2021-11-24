package Backend.Classes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Document(collection = "Vehicle")
public class Vehicle {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @Getter
    @Setter
    private String brand;
    @Getter
    @Setter
    private String lplate;
    @Getter
    @Setter
    private String color;

    public Vehicle(String id, String brand, String lplate, String color) {
        this.id = id;
        this.brand = brand;
        this.lplate = lplate;
        this.color = color;
    }
}
