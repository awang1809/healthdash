package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents user data about the program that can be saved
public class UserData implements Writable {
    private String metric;
    private ArrayList<String> selectedRestaurants;
    private ArrayList<String> unselectedRestaurants;
    private ArrayList<String> favouriteItems;

    // EFFECTS: constructs this object with given metric, list of selected and unselected restaurants, and list of items
    public UserData(String metric, ArrayList<String> s, ArrayList<String> u, ArrayList<String> f) {
        this.metric = metric;
        this.selectedRestaurants = s;
        this.unselectedRestaurants = u;
        this.favouriteItems = f;
    }
    
    public String getMetric() {
        return metric;
    }

    public ArrayList<String> getSelectedRestaurants() {
        return selectedRestaurants;
    }

    public ArrayList<String> getUnselectedRestaurants() {
        return unselectedRestaurants;
    }

    public ArrayList<String> getFavourites() {
        return favouriteItems;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("metric", metric);
        json.put("selected", listToJson(selectedRestaurants));
        json.put("unselected", listToJson(unselectedRestaurants));
        json.put("favourites", listToJson(favouriteItems));
        return json;
    }

    // EFFECTS: returns an arraylist of strings as a JSON array
    private JSONArray listToJson(ArrayList<String> list) {
        JSONArray jsonArray = new JSONArray();
        for (String s : list) {
            JSONObject json = new JSONObject();
            json.put("name", s);
            jsonArray.put(json);
        }
        return jsonArray;
    }
}
