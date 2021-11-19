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
import java.util.List;

import org.bson.Document;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class APIApp {
    public static void main(String[] args)
    {
        //SpringApplication.run(APIApp.class, args);
        /*ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase database = mongoClient.getDatabase("Crossyn");

        //MongoClient mongoClient = new MongoClient(connectionString);
        //MongoDatabase database = mongoClient.getDatabase("Crossyn");
        MongoCollection<Document> collection = database.getCollection("Trips");

        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                .append("info", new Document("x", 203).append("y", 102));

        collection.insertOne(doc);*/


    }

}
