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

        System.out.println("Please select 1/2/3/4/5/6/7 to choose a dataset");
        int set = input.nextInt();

        switch (set) {
            case 1:
                bufReader = new BufferedReader(new FileReader("src\\main\\java\\Backend\\DataStream\\dataset1.txt"));
                break;
            case 2:
                bufReader = new BufferedReader(new FileReader("src\\main\\java\\Backend\\DataStream\\dataset2.txt"));
                break;
            case 3:
                bufReader = new BufferedReader(new FileReader("src\\main\\java\\Backend\\DataStream\\dataset3.txt"));
                break;
            case 4:
                bufReader = new BufferedReader(new FileReader("src\\main\\java\\Backend\\DataStream\\dataset4.txt"));
                break;
            case 5:
                bufReader = new BufferedReader(new FileReader("src\\main\\java\\Backend\\DataStream\\dataset5.txt"));
                break;
            case 6:
                bufReader = new BufferedReader(new FileReader("src\\main\\java\\Backend\\DataStream\\dataset6.txt"));
                break;
            case 7:
                bufReader = new BufferedReader(new FileReader("src\\main\\java\\Backend\\DataStream\\dataset7.txt"));
                break;
        }


        String line = bufReader.readLine();
        String finalLine = "";
        while (line != null) {
            finalLine = finalLine + line;
            line = bufReader.readLine();
        }
        bufReader.close();
        return finalLine;

    }

    public String BigLineDialog() throws IOException {
        BufferedReader bufReader = null;

        JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // set the label to the path of the selected directory
            bufReader = new BufferedReader(new FileReader(fc.getSelectedFile().getAbsolutePath()));
        }

        String line = bufReader.readLine();
        String finalLine = "";
        while (line != null) {
            finalLine = finalLine + line;
            line = bufReader.readLine();
        }
        bufReader.close();
        return finalLine;

    }

    public String TurnDatasetIntoString() throws IOException {

        BufferedReader bufReader = null;
        Scanner input = new Scanner(System.in);
        String finalLine = null;
        bufReader = new BufferedReader(new FileReader("src\\main\\java\\Backend\\DataStream\\dataset1.txt"));





        String line = bufReader.readLine();

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