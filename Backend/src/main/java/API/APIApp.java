package API;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.ValidationOptions;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bson.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={
        "Backend", "API"})
public class APIApp {
    public static void main(String[] args)
    {
        SpringApplication app = new SpringApplication(APIApp.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8083"));
        app.run(args);
    }

}
