package Backend.Classes;

import com.mongodb.lang.Nullable;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


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
    @Nullable
    private ZonedDateTime endTime;
    private boolean currentlyOngoing;
    private int speedLimitBreakCounter;
    private int averageSpeed;
    private int averageRoad;
    private String startAddress;
    private String endAddress;


    private Vehicle vehicle;

    private ArrayList<TripEntry> Entries;


    public Trip(String id ,String vehicleId, ZonedDateTime startTime, ZonedDateTime endTime, boolean currentlyOngoing) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currentlyOngoing = currentlyOngoing;
        Entries = new ArrayList<TripEntry>();
    }

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


    public void setid(String id){this.id = id;}

    public String getid() {
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

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public boolean AddTripEntry(TripEntry a) {
        if (a != null){
            Entries.add(a);
            //check size, if true then put all except 3 in db
            if(Entries.size() >= 10)
            {
                this.Entries = this.SortbyTime(Entries); //sorts

            }
            return true;
        }
        else {
            return false;
        }

    }

    public boolean RemoveTripEntry(TripEntry a) {
        if (a != null){
            Entries.remove(a);
            return true;
        }
        else {
            return false;
        }
    }

    public TripEntry GetLatestTripEntry()
    {
        this.Entries = this.SortbyTime(Entries); //sorts
        return Entries.get(0);
    }

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

    public List<TripEntry> SortbyTime(List<TripEntry> entries)
    {
        Collections.sort(entries, new Comparator<TripEntry>() {
            public int compare(TripEntry o1, TripEntry o2) {
                return o2.getDateTime().compareTo(o1.getDateTime());
            }
        });
        return entries;
    }

    public ArrayList<TripEntry> SortbyTime(ArrayList<TripEntry> entries)
    {
        Collections.sort(entries, new Comparator<TripEntry>() {
            public int compare(TripEntry o1, TripEntry o2) {
                return o2.getDateTime().compareTo(o1.getDateTime());
            }
        });
        return entries;
    }


}
