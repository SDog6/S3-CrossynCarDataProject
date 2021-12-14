package Backend;

import Backend.Accepter.AccepterFlow;
import Backend.Accepter.TripEntryAccepter;
import Backend.Algorithm.TripEntryAlgorithm;
import Backend.Classes.Trip;
import Backend.Classes.TripEntry;
import Backend.Containers.TripContainer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


@SpringBootApplication(scanBasePackages={
        "Backend", "API"})
public class CrossynApp {


    private static TripEntryAlgorithm Algorithm;

    @Autowired
    private TripContainer t;

    public CrossynApp(TripEntryAlgorithm Algorithm)
    {
        this.Algorithm = Algorithm;
    }

//    @RequestMapping("/")
//    public String home() {
//        String test = "";
//        for (Trip item : t.dbFetchAllTripSummaries())
//        {
//            test = test + item;
//        }
//        return test;
//    }

    public static void main(String[] args) throws IOException {
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
        if (set) {
            list = TE.TurnJSONStringToObject(TE.BigLineDialog());
        } else {
            list = TE.TurnJSONStringToObject(TE.BigLine());
        }


        for (TripEntry Test : list) {
            try {
                //queue.add(Test);
                if (queue.isEmpty()) {
                    synchronized (queue) {
                        queue.notify(); // notify and wake the algorithm

                    }
                }

                queue.put(Test);
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }


    }}