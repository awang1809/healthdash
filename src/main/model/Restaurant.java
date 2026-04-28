package model;

import java.util.ArrayList;

// Represents information for a single restaurant
public class Restaurant {
    private String name;
    private ArrayList<Item> items;

    // EFFECTS: creates an instance of this class with given name and list of items
    public Restaurant(String name, ArrayList<Item> items) {
        this.name = name;
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }
}
