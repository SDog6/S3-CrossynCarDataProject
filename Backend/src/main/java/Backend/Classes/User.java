package Backend.Classes;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "User")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userID;

    private String username;
    private String password;
    private boolean admin;
    private List<String> connectedTripIDs;
    private List<String> activeVehicles;
    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return userID;
    }

    public void setId(Long userID) {
        this.userID = userID;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

}
