package Backend.Classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.ZonedDateTime;
import java.util.ArrayList;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection= "Trips")
public class Trip{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String vehicleId;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private boolean currentlyOngoing;
    private int speedLimitBreakCounter;
    private int averageSpeed;
    private int averageRoad;


    private Vehicle vehicle;

    private ArrayList<TripEntry> Entries;


    public Trip(String vehicleId, ZonedDateTime startTime, ZonedDateTime endTime, boolean currentlyOngoing) {
        this.vehicleId = vehicleId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currentlyOngoing = currentlyOngoing;
        Entries = new ArrayList<TripEntry>();
    }

    public Trip(String vehicleId, ZonedDateTime startTime, ZonedDateTime endTime, boolean currentlyOngoing,String s) {
        this.vehicleId = vehicleId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currentlyOngoing = currentlyOngoing;
    }

    public Trip(Vehicle vehicle, ZonedDateTime startTime, ZonedDateTime endTime, boolean currentlyOngoing) {
        this.vehicle = vehicle;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currentlyOngoing = currentlyOngoing;
        Entries = new ArrayList<TripEntry>();
    }


    public void setId(String id){this.id = id;}

    public String getID() {
        return id;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public boolean isCurrentlyOngoing() {
        return currentlyOngoing;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public void setCurrentlyOngoing(boolean currentlyOngoing) {
        this.currentlyOngoing = currentlyOngoing;
    }

    public int getSpeedLimitBreakCounter() {
        return speedLimitBreakCounter;
    }

    public void setSpeedLimitBreakCounter(int speedLimitBreakCounter) {
        this.speedLimitBreakCounter = speedLimitBreakCounter;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public int getAverageRoad() {
        return averageRoad;
    }

    public void setAverageRoad(int averageRoad) {
        this.averageRoad = averageRoad;
    }

    public boolean AddTripEntry(Object a) {
        if (a != null){
            Entries.add((TripEntry) a);
            return true;
        }
        else {
            return false;
        }

    }

    public boolean RemoveTripEntry(Object a) {
        if (a != null){
            Entries.remove((TripEntry) a);
            return true;
        }
        else {
            return false;
        }
    }

    public TripEntry GetLatestTripEntry() {return Entries.get(Entries.size() -1); }

    public ArrayList<Object> GetAllTripEntries() {
        ArrayList<Object> replacement = new ArrayList<Object>();

        for (TripEntry a: Entries) {
            replacement.add(a);
        }
        return replacement;
    }

    @Override
    public String toString(){
        if(this.currentlyOngoing == true){
            return "Trip with vehicle id: "+ this.getVehicleId() + " Started at : " + this.getStartTime() + " and ended at : " + this.getEndTime() + " the status is: Ongoing";
        }
        else {
            return "Trip with vehicle id: "+ this.getVehicleId() + " Started at : " + this.getStartTime() + " and ended at : " + this.getEndTime() + " the status is: Stopped";

        }
    }


}
