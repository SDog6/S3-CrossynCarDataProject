package Backend.Algorithm;


import Backend.Classes.CorruptLocationFilter;
import Backend.Classes.Trip;
import Backend.Classes.TripEntry;
import Backend.Containers.TripContainer;
import Backend.Containers.TripEntryContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.chrono.ChronoZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

@Service
public class AlgorithmHandler
{

    //filter
    public CorruptLocationFilter filter = new CorruptLocationFilter();
    //Things
    @Autowired
    public TripContainer t;
    //Constructor
    @Autowired
    public AlgorithmHandler()//TripContainer t)
    {
        //this.t = t;//new TripContainer();
    }

    private Trip ProcessingTrip;
    private TripEntry PrevEntry;

    //Functions

    public boolean Add2Trip(@org.jetbrains.annotations.NotNull TripEntry entry) //add entry to trip
    {
        //I do not know what the note under this means anymore but it sounds important so leaving it here for now
        //this.IncomingEntry = tripEntry; //TODO: instead of directly putting it in Incoming run it throught the algorithm first (so TripEntryAlgorithm.Start(TripEntry))

         if(t.VehicleOnTrip(entry.getVehicleID())) //check if trip is ongoing
        {
            ProcessingTrip = t.GetOngoingTripFromVehicleID(entry.getVehicleID());
            PrevEntry = ProcessingTrip.GetLatestTripEntry();

            //if the entry is corrupt a fakeEntry is provided else proceed as normal
            TripEntry falseEntry = filter.doFilter(ProcessingTrip.getEntries());
            if (falseEntry != null)
            {
                entry = falseEntry; //now when you save the entry you'll save the false entry instead
            }

            Duration between = Duration.between(PrevEntry.getDateTime().toLocalTime(),entry.getDateTime().toLocalTime()); //check if entry is older then 5 minutes

            if( between.toMinutes() >= 5 || between.toMinutes() <= -3) //if difference is bigger than 5 minutes or smaller than -3 minutes
            {
                t.FinishUpTrip(ProcessingTrip);
                return true; //trip has ended
            }

        }
        else
        {
            ProcessingTrip = t.CreateTrip(entry.getVehicleID(), entry.getDateTime(), entry.getDateTime(), true);
            t.AddTrip(ProcessingTrip);
            t.dbSaveTrip(ProcessingTrip);
        }

        t.AddToTripWithVehicleID(entry.getVehicleID(), entry);
        return false; //trip has not ended

    }

    public void EndTrip()
    {
        ProcessingTrip.setEndTime(PrevEntry.getDateTime());


        //ProcessingTrip.setCurrentlyOngoing(false); //update completed trip details
        //Add2Trip(entry); //start this funtions again to create a new trip with the incoming entry (that still isn't handled)
        //t.dbSaveTrip(ProcessingTrip);


    }
}
