package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

// Tests the UserData class
public class UserDataTest {
    private String metric;
    private ArrayList<String> selectedRestaurants;
    private ArrayList<String> unselectedRestaurants;
    private ArrayList<String> favouriteItems;
    private UserData testUserData;

    @BeforeEach
    void runBefore() {
        metric = "test";
        selectedRestaurants = new ArrayList<>();
        selectedRestaurants.add("aw");
        unselectedRestaurants = new ArrayList<>();
        unselectedRestaurants.add("mcdonalds");
        favouriteItems = new ArrayList<>();
        favouriteItems.add("Big Mac");
        testUserData = new UserData(metric, selectedRestaurants, unselectedRestaurants, favouriteItems);
    }

    @Test
    void testConstructor() {
        assertEquals(metric, testUserData.getMetric());
        assertEquals(selectedRestaurants, testUserData.getSelectedRestaurants());
        assertEquals(unselectedRestaurants, testUserData.getUnselectedRestaurants());
        assertEquals(favouriteItems, testUserData.getFavourites());
    }
}
