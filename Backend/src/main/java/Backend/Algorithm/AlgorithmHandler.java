package Backend.Algorithm;


import Backend.Classes.Trip;
import Backend.Classes.TripEntry;
import Backend.Containers.TripContainer;
import Backend.Containers.TripEntryContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class AlgorithmHandler
{
    //Things
    @Autowired
    public TripContainer t;

    //Constructor
    @Autowired
    public AlgorithmHandler()//TripContainer t)
    {

        //this.t = t;//new TripContainer();
    }

    //Functions

    public boolean Add2Trip(@org.jetbrains.annotations.NotNull TripEntry entry) //add entry to trip
    {
        //I do not know what the note under this means anymore but it sounds important so leaving it here for now
        //this.IncomingEntry = tripEntry; //TODO: instead of directly putting it in Incoming run it throught the algorithm first (so TripEntryAlgorithm.Start(TripEntry))

        Trip ProcessingTrip;
        TripEntry PrevEntry;
         if(t.VehicleOnTrip(entry.getVehicleID())) //check if trip is ongoing
        {
            ProcessingTrip = t.GetOngoingTripFromVehicleID(entry.getVehicleID());
            PrevEntry = ProcessingTrip.GetLatestTripEntry();

            Duration between = Duration.between(PrevEntry.getDateTime().toLocalTime(),entry.getDateTime().toLocalTime()); //check if entry is older then 5 minutes

            if( between.toMinutes() >= 5)
            {
                ProcessingTrip.setEndTime(PrevEntry.getDateTime());
                ProcessingTrip.setCurrentlyOngoing(false); //update completed trip details
                //Add2Trip(entry); //start this funtions again to create a new trip with the incoming entry (that still isn't handled)
                t.dbSaveTrip(ProcessingTrip);
                return true; //trip has ended
            }

        }
        else
        {
            ProcessingTrip = t.CreateTrip(entry.getVehicleID(), entry.getDateTime(), null, true);
            t.AddTrip(ProcessingTrip);
        }

        t.AddToTripWithVehicleID(entry.getVehicleID(), entry);
        return false; //trip has not ended

    }
}
