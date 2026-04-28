package persistence;

import model.UserData;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads userdata: favouriteitems, metric, and selected restaurants from JSON data
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads userdata from file and returns it;
    // throws IOException if an error occurs reading data from file
    public UserData read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUserData(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses userdata from JSON object and returns it
    private UserData parseUserData(JSONObject jsonObject) {
        String metric = jsonObject.getString("metric");
        ArrayList<String> selected = convertJsonArray("selected", jsonObject);
        ArrayList<String> unselected = convertJsonArray("unselected", jsonObject);
        ArrayList<String> favourites = convertJsonArray("favourites", jsonObject);
        UserData u = new UserData(metric, selected, unselected, favourites);
        return u;
    }

    // EFFECTS: parses specified keys corresponding to arrays from JSON object, returns arraylist of strings
    private ArrayList<String> convertJsonArray(String key, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray(key);
        ArrayList<String> toReturn = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            String name = object.getString("name");
            toReturn.add(name);
        }
        return toReturn;
    }
}
