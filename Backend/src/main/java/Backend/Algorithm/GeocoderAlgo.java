package Backend.Algorithm;

import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@NoArgsConstructor
public class GeocoderAlgo {


    //THIS THING WILL EXPIRE AND THEN WE ARE FUCKED
    //MAKE SURE YOU CHANGE THIS AND TEST IT ON THE DAY OF PRESENTATION!!!!!!!!
    private String apikey = "AAx9K26UdV5RwNZTbBVrWW2RkU0uBjFfl0UmfnfW6f4";
    // Coordinates link generator
    // Does not require api key since Nominatim is open source
    private String Stringify(String lat, String lon) {
        return "https://revgeocode.search.hereapi.com/v1/revgeocode?lang=en-US&at=" + lat + "%2C" + lon + "&apikey=" + apikey;
    }

    public String FindAddress(String lat, String lon) throws IOException, JSONException {
        // HTTP Get request - Uses Stringify method to make custom URL with provided coordinates
        URL request = new URL(Stringify(lat, lon));
        // Open Connection
        URLConnection rq = request.openConnection();
        // Save Returned Data in inputLine
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        rq.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null)
            //System.out.println(inputLine);
            response.append(inputLine);
        // Close Connection - Important!
        in.close();
        // Convert the String response into a JSON object
        JSONObject myResponse = new JSONObject(response.toString());
        // Navigate through JSON object
        // Save all needed fields to string
        JSONArray temp = myResponse.getJSONArray("items");
        JSONObject inside = temp.getJSONObject(0);
        String result = inside.getString("title");

        return result;
    }
}
