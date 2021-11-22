package Backend.Algorithm;

import Backend.Classes.Trip;
import Backend.Classes.TripEntry;
import Backend.Containers.TripContainer;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.*;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

@NoArgsConstructor
@Service
public class TripEntryAlgorithm implements Runnable {



    @Setter
    BlockingQueue<TripEntry> queue;
    //Queue<TripEntry> queue;


    @Autowired
    private TripContainer t;
    @Autowired
    private AlgorithmHandler h;

    @Autowired
    private MongoTemplate mt;


    /*
    public TripEntryAlgorithm(BlockingQueue<TripEntry> queue)
    {
        this.queue = queue;
        //t = new TripContainer();
        //h = new AlgorithmHandler(t);

    }

    public TripEntryAlgorithm(Queue<TripEntry> queue)
    {
        this.queue = queue;
        //t = new TripContainer();
        //h = new AlgorithmHandler(t);

    }*/


    @Override
    public void run()
    {

        testing();

        while(true) //queue.peek() != null) //set to true when threading again
        {

            TripEntry entry;

            try
            {
                synchronized(queue) {
                    while (queue.isEmpty())
                        queue.wait(); //wait for the queue to become empty
                }

                 entry = queue.take();
                 //entry = queue.poll();
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
            catch(NullPointerException ex2)
            {
                System.out.println(ex2);
                ex2.printStackTrace();
            }



        }
    }

    private void testing()
    {
        //System.out.println(t.dbGetTrip("61978d3b475e6315eb8ddd6d"));

        //List<Trip> list = mt.findAll(Trip.class);
        //List<Trip> list = mt.findAll(Trip.class,"Trips");  //If collection name & the Entity Class Name are different (case-sensitive)
        //list.forEach(System.out::println);

        Query query= new Query();
        query.addCriteria(Criteria.where("vehicleId").is("00A12"));

        Trip details = mt.findOne(query, Trip.class, "Trips");
        TripEntry te = new TripEntry("a", 5, 5, 3, ZonedDateTime.now(), 5, 4, 5, false);
        //int i = 0; for (TripEntry entry: details.getEntries()) {entry.setAlt(i);  i++; }
        Update update = new Update();
        update.addToSet("Entries", te);

        mt.findAndModify(query, update, Trip.class, "Trips");
        System.out.println("Data Modified");
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
