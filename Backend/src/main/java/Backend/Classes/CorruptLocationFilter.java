package Backend.Classes;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class CorruptLocationFilter {
    private double maxSpeed = 300; // KM/H

    public CorruptLocationFilter() {}

    //calculates the distance between 2 points on a sphere
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

    public double avgDifference(double data1, double data2, double data3)
    {
        return (data1+data2+data3)/3;
    }

    public double calculateSpeed(double time, double distance) // time in seconds : distance in KM
    {
        return distance/(time/3600); // KM/H
    }

    public TripEntry createFalseTripEntry(TripEntry entry, double lat, double lon, double alt)
    {
        TripEntry fakeEntry = new TripEntry(entry.getVehicleID(), lat, lon, (int) alt, entry.getDateTime(), entry.getSpeed(), entry.getSpeedlimit(), entry.getRoadType(), entry.isIgnition());
        fakeEntry.setFake(true);
        return fakeEntry;
    }

    public double calculateTime(ZonedDateTime t1, ZonedDateTime t2)
    {
        return ChronoUnit.SECONDS.between(t1, t2);
    }

    public TripEntry doFilter(List<TripEntry> data) //TODO: maybe add a trySwitch method which checks the speed if the long and lat were changed
    {
        //get data from parameter
        TripEntry first = data.get(0);
        TripEntry second = data.get(1);
        TripEntry third = data.get(2);

        if (first.getFake() == true) // if working with fake data increase the margin
        {
            maxSpeed = 350;
        }

        //calculate the speed
        double distance = calculateDistance(first.getLat(), first.getLon(), second.getLat(), second.getLon());
        double time = calculateTime(first.getDateTime(), second.getDateTime());
        double speed = calculateSpeed(distance, time);

        //if the car was too fast then the data was corrupt otherwise give back nothing
        if (speed > maxSpeed || speed < 0)
        {
            maxSpeed = 300;
            //create the fake location for the fake data with the average change
            double fakeAlt = first.getAlt()+avgDifference(first.getAlt(), second.getAlt(), third.getAlt());
            double fakeLon = first.getLon()+avgDifference(first.getLon(), second.getLon(), third.getLon());
            double fakeLat = first.getAlt()+avgDifference(first.getAlt(), second.getAlt(), third.getLat());

            return createFalseTripEntry(first, fakeLat, fakeLon, fakeAlt); //return a fake entry with the fake location and the corrupt data entry
        }
        maxSpeed = 300;
        return null;
    }
}
