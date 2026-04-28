package model;

import java.util.ArrayList;

// Represents restaurants that are either selected or not selected
public class RestaurantSelector {
    ArrayList<Restaurant> unselectedRestaurants;
    ArrayList<Restaurant> selectedRestaurants;
    
    // EFFECTS: creates an instance of this class with all restaurants not selected
    public RestaurantSelector(ArrayList<Restaurant> restaurants) {
        unselectedRestaurants = restaurants;
        selectedRestaurants = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: selects the given restaurant if it is unselected, else do nothing
    public void selectRestaurant(Restaurant restaurant) {
        if (!selectedRestaurants.contains(restaurant)) {
            selectedRestaurants.add(restaurant);
            unselectedRestaurants.remove(restaurant);
            EventLog.getInstance().logEvent(new Event("Selected " + restaurant.getName()));
        }
    }

        // MODIFIES: this
    // EFFECTS: selects the given restaurant with its name if it is unselected, else do nothing
    public void selectRestaurant(String restaurant) {
        for (Restaurant r : unselectedRestaurants) {
            if (r.getName().equals(restaurant)) {
                selectRestaurant(r);
                break;
            }
        }
    }
    
    // MODIFIES: this
    // EFFECTS: unselects the given restaurant if it is selected, else do nothing
    public void unselectRestaurant(Restaurant restaurant) {
        if (!unselectedRestaurants.contains(restaurant)) {
            unselectedRestaurants.add(restaurant);
            selectedRestaurants.remove(restaurant);
            EventLog.getInstance().logEvent(new Event("Unselected " + restaurant.getName()));
        }
    }

    // MODIFIES: this
    // EFFECTS: unselects the given restaurant with its name if it is selected, else do nothing
    public void unselectRestaurant(String restaurant) {
        for (Restaurant r : selectedRestaurants) {
            if (r.getName().equals(restaurant)) {
                unselectRestaurant(r);
                break;
            }
        }
    }

    // EFFECTS: returns true if restaurant is selected, false otherwise
    public Boolean isSelected(String restaurant) {
        Boolean toReturn = false;
        for (Restaurant r : selectedRestaurants) {
            if (r.getName().equals(restaurant)) {
                toReturn = true;
            }
        }
        return toReturn;
    }

    public ArrayList<Restaurant> getSelected() {
        return selectedRestaurants;
    }

    public ArrayList<Restaurant> getUnselected() {
        return unselectedRestaurants;
    }
}
