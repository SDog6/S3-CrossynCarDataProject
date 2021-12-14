package Backend.Algorithm;

import Backend.Classes.Trip;
import Backend.Classes.TripEntry;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Algorithm
{
    private List<Trip> trips=new ArrayList<Trip>();

    public Algorithm(){
        trips = new ArrayList<>();
    }

    public List<Trip> MakeTrips (List<TripEntry> entries) {
        TripEntry OldEntry = null;
        int count = 0;


        for (TripEntry entry : entries) {

            //If there are no trips make one
            if (trips.stream().count() > 0) {
                for (Trip currenttrips : trips) {

                    // Check all ongoing trips if this trip entry belongs to them
                    if (currenttrips.isCurrentlyOngoing() == true){
                        if (currenttrips.getVehicleId().equals(entry.getVehicleID())) {
                            currenttrips.AddTripEntry(entry);
                        }
                    }
                }

                //Check to see if ALL Trips have currently stopped
                boolean allEnded = trips.stream().allMatch(x -> x.isCurrentlyOngoing() == false);

                //If all trips have stopped but there is a new trip entry make a new trip
                if (allEnded == true){
                    Trip newTrip = new Trip(entry.getVehicleID(), entry.getDateTime(), null, true);
                    trips.add(newTrip);
                    OldEntry = null;
                }

                if(OldEntry == null)
                {
                    OldEntry = entry;
                }
                else
                {

                    //The trick with OldEntry.time.getMinutes() - NewEntry.Time.getMinutes() does not work because 10:59 - 11:00 = 59 minutes difference
                    // Neither does NewEntry.Time.getMinutes() - OldEntry.time.getMinutes() because 15:02 - 14:37 = -37 minutes difference

                    Duration between = Duration.between(OldEntry.getDateTime().toLocalTime(),entry.getDateTime().toLocalTime());
                    if( between.toMinutes() >= 5)
                    {

                        for (Trip currenttrips : trips)
                        {
                            if (currenttrips.isCurrentlyOngoing() == true)
                            {
                                if (currenttrips.getVehicleId().equals(entry.getVehicleID()))
                                {
                                    currenttrips.setEndTime(OldEntry.getDateTime());
                                    currenttrips.setCurrentlyOngoing(false);
                                }
                           }
                        }
                    }
                }
                    OldEntry = entry;

            }
            else {
                Trip newTrip = new Trip(entry.getVehicleID(), entry.getDateTime(), null, true);
                trips.add(newTrip);
            }
        }
        return trips;
    }

}
