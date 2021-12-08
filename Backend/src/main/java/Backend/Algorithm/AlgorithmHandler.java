package Backend.Algorithm;


import Backend.Classes.Trip;
import Backend.Classes.TripEntry;
import Backend.Containers.TripContainer;
import Backend.Containers.TripEntryContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

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

            //Speed Limit Break Calculation
            if(entry.getSpeed() > entry.getSpeedlimit()){
                int speedBreak = ProcessingTrip.getSpeedLimitBreakCounter() + 1;
                ProcessingTrip.setSpeedLimitBreakCounter(speedBreak);
            }
            //Speed Limit Break Calculation END


            //Average Speed Calculation
            int counterSpeed = 0;
            int counterNum = 0;
            for (TripEntry t: ProcessingTrip.getEntries()) {
                counterSpeed += t.getSpeed();
                counterNum++;
            }
            int Average = counterSpeed / counterNum;
            ProcessingTrip.setAverageSpeed(Average);
            //Average Speed Calculation END

            //Average Road Type Calculation
            List<Integer> Roads = new ArrayList<>();
            int largest = 0 ;
            for (TripEntry t : ProcessingTrip.getEntries()) {
               Roads.add(t.getRoadType());
            }
            Map<Integer, Integer> map = new HashMap<Integer, Integer>();
            for (int i : Roads) {
                Integer count = map.get(i);
                map.put(i, count != null ? count+1 : 1);
            }
            Integer popular = Collections.max(map.entrySet(),
                    new Comparator<Map.Entry<Integer, Integer>>() {
                        @Override
                        public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                            return o1.getValue().compareTo(o2.getValue());
                        }
                    }).getKey();
            ProcessingTrip.setAverageRoad(popular);
            //Average Road Type Calculation END

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
