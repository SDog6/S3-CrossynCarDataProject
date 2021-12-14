package Backend.Classes;

import java.time.ZonedDateTime;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class CorruptLocationFilter {

    private List<TripEntry> data;
    private double maxSpeed = 300; // KM/H

    public CorruptLocationFilter() {}

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2){
        double R = 6378.137; // Radius of earth in KM
        double dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180;
        double dLon = lon2 * Math.PI / 180 - lon1 * Math.PI / 180;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        return d; // in KM
    }

    public double avgDifference(double data1, long data2, long data3)
    {
        return (data1+data2+data3)/3;
    }

    public double calculateSpeed(long time, long distance) // time in seconds : distance in KM
    {
        return distance/(time/3600); // KM/H
    }

    public TripEntry createFalseTripEntry(double lat, double lon , int alt, ZonedDateTime time)
    {
        return new TripEntry(null, lat, lon, alt, time, -1, -1, -1, false);
    }

}
