package Backend;
import Backend.Accepter.TripEntryAccepter;
import Backend.Algorithm.Algorithm;
import Backend.Algorithm.TripEntryAlgorithm;
import Backend.Classes.Trip;
import Backend.Classes.TripEntry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


@SpringBootApplication()
@RestController
public class CrossynApp {


    private static TripEntryAlgorithm Algorithm;


    public CrossynApp(TripEntryAlgorithm Algorithm) {
        this.Algorithm = Algorithm;
    }

    @RequestMapping("/")
    public String home()
    {
        return "Hello Coker World";
    }

    public static void main(String[] args) throws IOException
    {
        SpringApplication.run(CrossynApp.class, args);

        BlockingQueue<TripEntry> queue = new ArrayBlockingQueue(10000);
        new Thread(Algorithm).start();
        Algorithm.setQueue(queue);
        System.out.println("Debug purpose; You want to use dialog popup to select dataset? (write true or false)");
        Scanner input = new Scanner(System.in);
        boolean set = input.nextBoolean();
           // while(true){

                //Algorithm test1 = new Algorithm();
                TripEntryAccepter TE = new TripEntryAccepter();

                //String finalLine = TE.BigLine();

                List<TripEntry> list;
                if(set)
                {
                    list = TE.TurnJSONStringToObject(TE.BigLineDialog());
                }
                else
                {
                    list = TE.TurnJSONStringToObject(TE.BigLine());
                }




                for(TripEntry Test : list)
                {
                    try
                    {
                        //queue.add(Test);
                        if(queue.isEmpty())
                        {
                            synchronized(queue) {
                                queue.notify(); // notify the producer
                            }
                        }

                        queue.put(Test);
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }

                }
                //Algorithm.setQueue(queue);

                //TripEntryAlgorithm Algorithm = new TripEntryAlgorithm(queue);
                //Algorithm.run();


                //List<Trip> Test = new ArrayList<Trip>();

                //Test = test1.MakeTrips(list);
                //for (Trip test2 : Test) {
                //    System.out.println(test2);
                //}
            //}

        }

//
// TODO: ASKING YOU IF YOU WANT TO SEE WHEN YOU BREAK THE SPEED LIMIT

//    public static boolean Prompt() throws IOException {
//        // Enter data using BufferReader
//        BufferedReader reader = new BufferedReader(
//                new InputStreamReader(System.in));
//
//        // Reading data using readLine
//        String input = reader.readLine();
//        if(input.equals("Y")||input.equals("y"))
//        {
//            return true;
//        }
//        else if (input.equals("N")||input.equals("n"))
//        {
//            return false;
//        }
//        else
//        {
//            System.out.println("Please Try again");
//            return Prompt();
//        }
//
//    }


//     TODO:           SpeedLimit Breaking

//    List<TripEntry> BrokenSpeeds = new LinkedList<>();
//
//                for (TripEntry entry : list) {
//        if (entry.getSpeed() > entry.getSpeedlimit())
//        {
//            BrokenSpeeds.add(entry);
//        }
//    }
//                System.out.println("Speed Limit broken: " + BrokenSpeeds.size());
//                System.out.println("Do you want to see the entries Where speed was broken? (Y/N)");
//                if(Prompt())
//    {
//        for (TripEntry Entry : BrokenSpeeds)
//        {
//            System.out.println(Entry);
//        }
//    }
//}
    }




