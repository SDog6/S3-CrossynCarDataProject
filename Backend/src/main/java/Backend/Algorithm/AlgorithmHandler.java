package Backend.Algorithm;


import Backend.Classes.CorruptLocationFilter;
import Backend.Classes.Trip;
import Backend.Classes.TripEntry;
import Backend.Classes.User;
import Backend.Containers.TripContainer;
import Backend.Containers.TripEntryContainer;
import Backend.Interfaces.IUser;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.*;

@Service
public class AlgorithmHandler
{

    //filter
    public CorruptLocationFilter filter = new CorruptLocationFilter();
    //Things
    @Autowired
    public TripContainer t;

    @Autowired
    IUser UserRep;

    //Constructor
    @Autowired
    public AlgorithmHandler()
    {
    }

    //Functions

    public boolean Add2Trip(@NotNull TripEntry entry) throws IOException //add entry to trip
    {


        Trip ProcessingTrip;
        TripEntry PrevEntry;
        GeocoderAlgo geo = new GeocoderAlgo();
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
            //if the entry is corrupt a fakeEntry is provided else proceed as normal
            TripEntry falseEntry = filter.doFilter(ProcessingTrip.getEntries());
            if (falseEntry != null)
            {
                entry = falseEntry; //now when you save the entry you'll save the false entry instead
            }

            Duration between = Duration.between(PrevEntry.getDateTime().toLocalTime(),entry.getDateTime().toLocalTime()); //check if entry is older then 5 minutes
            if( between.toMinutes() >= 5)
            {
                ProcessingTrip.setEndTime(PrevEntry.getDateTime());
                ProcessingTrip.setCurrentlyOngoing(false); //update completed trip details
                //Add2Trip(entry); //start this funtions again to create a new trip with the incoming entry (that still isn't handled)
                String result = geo.FindAddress(String.valueOf(entry.getLat()),String.valueOf(entry.getLon()));
                ProcessingTrip.setEndAddress(result);
                User temp = UserRep.getUserByConnectedVehiclesAndRole(ProcessingTrip.getVehicleId(),"DRIVER");
                if (temp != null) {
                    ProcessingTrip.setDriver(temp.getUsername());
                }

                System.out.println(between);
                 t.FinishUpTrip(ProcessingTrip);
                return true; //trip has ended
            }

        }
        else
        {
            ProcessingTrip = t.CreateTrip(entry.getVehicleID(), entry.getDateTime(), entry.getDateTime(), true);
            String result = geo.FindAddress(String.valueOf(entry.getLat()),String.valueOf(entry.getLon()));
            ProcessingTrip.setStartAddress(result);
            ProcessingTrip.setEndAddress("Error; to be determined.");
            ProcessingTrip.setDriver("Error; to be determined.");

            t.AddTrip(ProcessingTrip);
            t.dbSaveTrip(ProcessingTrip);
        }

        t.AddToTripWithVehicleID(entry.getVehicleID(), entry);

        return false; //trip has not ended

    }
}
