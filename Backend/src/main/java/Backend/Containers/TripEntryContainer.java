package Backend.Containers;

import Backend.Classes.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class TripEntryContainer
{
    //facts
    private List<TripEntry> tripEntryList=new ArrayList<TripEntry>();

    //Functions (Crud)
    public TripEntry AddTripEntry(String vehicleId, double lat, double lon, int alt, ZonedDateTime dateTime, int speed, int speedLimit, int roadType, boolean ignition)
    {
        TripEntry tripEntry = CreateTripEntry(vehicleId, lat, lon, alt, dateTime, speed, speedLimit, roadType, ignition);
        tripEntryList.add(tripEntry);
        return tripEntryList.get(tripEntryList.indexOf(tripEntry)); //FIXME: dunno if this will work...
    }
    public TripEntry CreateTripEntry(String vehicleId, double lat, double lon, int alt, ZonedDateTime dateTime, int speed, int speedLimit, int roadType, boolean ignition)
    {
        return new TripEntry(vehicleId, lat, lon, alt, dateTime, speed, speedLimit, roadType, ignition);
    }

    public List<TripEntry> ReadTripEntries()
    {
        return tripEntryList;
    }

    public boolean UpdateTripEntries(TripEntry oldEntry, TripEntry newEntry) //replace 1 item
    {
        try
        {
            tripEntryList.remove(oldEntry);

            try //if can't add new trip re-add old trip
            {
                tripEntryList.add(newEntry);
            }
            catch(Exception ex)
            {
                tripEntryList.add(oldEntry);
                return false;
            }

            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }

    public boolean DeleteTripEntry(TripEntry entry)
    {
        try
        {
            tripEntryList.remove(entry);
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }
}