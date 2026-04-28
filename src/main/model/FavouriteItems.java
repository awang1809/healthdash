package model;

import java.util.ArrayList;

// Represents a list of favourited items the user has created
public class FavouriteItems {
    ArrayList<Item> favourites;

    // EFFECTS: constructs a new object with empty list of favourites
    public FavouriteItems() {
        favourites = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds given item to the list of favourites if item not in list.
    public void addItem(Item i) {
        if (!favourites.contains(i)) {
            favourites.add(i);
            EventLog.getInstance().logEvent(new Event(i.getName() + " added to favourite items."));
        } 
    }

    public ArrayList<Item> getFavourites() {
        return favourites;
    }
}
