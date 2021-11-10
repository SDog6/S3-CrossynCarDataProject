package Backend.Algorithm;

import Backend.Classes.Trip;
import Backend.Classes.TripEntry;
import Backend.Containers.TripContainer;

import javax.swing.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class TripEntryAlgorithm implements Runnable {

    BlockingQueue<TripEntry> queue;

    private TripContainer t;
    private AlgorithmHandler h;

    public TripEntryAlgorithm(BlockingQueue<TripEntry> queue)
    {
        this.queue = queue;
        t = new TripContainer();
        h = new AlgorithmHandler(t);

    }

    @Override
    public void run()
    {

        while(true)
        {
            TripEntry entry;

            try
            {
                 entry = queue.take();
                 if(h.Add2Trip(entry))
                 {
                     System.out.println("Trip Finished: " + t.GetPastTripsFromVehicleID(entry.getVehicleID()).get(t.GetPastTripsFromVehicleID(entry.getVehicleID()).size() - 1));
                 }
            }
            catch(InterruptedException ex)
            {
                System.out.println(ex);
                ex.printStackTrace();
            }



        }
    }


    /*
    TripEntry OldEntry = null;
        for (TripEntry entry : list)
    {
        if(OldEntry == null)
        {
            OldEntry = entry;

        }
        else
        {
            if(entry.getDateTime().getHour() - OldEntry.getDateTime().getHour() >= 1){
                System.out.print(entry.getDateTime());
                System.out.println(OldEntry.getDateTime());

            }
            OldEntry = entry;
        }*/

}
