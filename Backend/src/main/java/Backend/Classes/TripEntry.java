package Backend.Classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.Document;

//import javax.persistence.*;
//import javax.persistence.Id;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripEntry {


    private Long id;
    private String vehicleId;
    private double lat;
    private double lon;
    private int alt;
    private ZonedDateTime dateTime;
    private int speed;
    private int speedLimit;
    private int roadType;
    private boolean ignition;


//for the algorithm
public TripEntry(String vehicleId, double lat, double lon, int alt, ZonedDateTime dateTime, int speed, int speedLimit, int roadType, boolean ignition)
{
    this.vehicleId = vehicleId;
    this.lat = lat;
    this.lon = lon;
    this.alt = alt;
    this.dateTime = dateTime;
    this.speed = speed;
    this.speedLimit = speedLimit;
    this.roadType = roadType;
    this.ignition = ignition;

}


    public void setId(Long id){this.id = id;}

    public Long getID() {
        return id;
    }

    public String getVehicleID() {
        return vehicleId;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public int getAlt() {
        return alt;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public int getSpeed() {
        return speed;
    }

    public int getSpeedlimit() {
        return speedLimit;
    }

    public int getRoadType() {
        return roadType;
    }

    public boolean isIgnition() {
        return ignition;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setAlt(int alt) {
        this.alt = alt;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public void setRoadType(int roadType) {
        this.roadType = roadType;
    }

    public void setIgnition(boolean ignition) {
        this.ignition = ignition;
    }

    public String toString(){
        return "Vehicle: " + getVehicleID().toString() + " at Latitude: " +getLat()+", Longitude: "+getLon()+" and Altitude: "+getAlt()+". Driver on RoadType: "+ getRoadType()+ " Driving: "+ getSpeed() +"Km/H, " + (getSpeed()>getSpeedlimit() ? "" : "Not ") + "Breaking the Speedlimit (Speedlimit: " + getSpeedlimit() + "Km/H) on " + getDateTime() + "ignition: "  + this.isIgnition();
    }
}
