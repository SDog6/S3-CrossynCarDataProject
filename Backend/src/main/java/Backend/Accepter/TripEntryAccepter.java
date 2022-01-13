package Backend.Accepter;

import Backend.Classes.TripEntry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.ToString;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class TripEntryAccepter {

    public TripEntryAccepter() {

    }


    public String BigLine() throws IOException {

        BufferedReader bufReader = null;

        Scanner input = new Scanner(System.in);

        System.out.println("Please choose a dataset");
        int set = input.nextInt();

        //bufReader = new BufferedReader(new FileReader("src\\main\\java\\Backend\\DataStream\\dataset".concat(String.valueOf(set)).concat(".txt")));
        bufReader = new BufferedReader(new FileReader("src\\main\\java\\Backend\\DataStream\\Splitted Dataset 1\\".concat(String.valueOf(set)).concat(".txt")));

            String line = bufReader.readLine();
            String finalLine = "";
            while (line != null) {
                finalLine = finalLine + line;
                line = bufReader.readLine();
            }
            bufReader.close();
            return finalLine;

    }

   

    public List<TripEntry> TurnJSONStringToObject(String JSON) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        TypeReference<List<TripEntry>> listType = new TypeReference<>() {};
        return mapper.readValue(JSON, listType);
    }
}