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


    public CrossynApp(TripEntryAlgorithm Algorithm)
    {
        this.Algorithm = Algorithm;
    }



    public static void main(String[] args) throws IOException
    {
        SpringApplication.run(CrossynApp.class, args);


        BlockingQueue<TripEntry> queue = new ArrayBlockingQueue(10000);
        new Thread(Algorithm).start();
        Algorithm.setQueue(queue);


        AccepterFlow Start = new AccepterFlow(queue);
        Start.init();

    }
}