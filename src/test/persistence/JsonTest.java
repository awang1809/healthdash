package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import model.UserData;
// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {
    protected void checkConstructor(String metric, ArrayList<String> s, 
            ArrayList<String> u, ArrayList<String> f, UserData userData) {
        assertEquals(metric, userData.getMetric());
        assertEquals(s, userData.getSelectedRestaurants());
        assertEquals(u, userData.getUnselectedRestaurants());
        assertEquals(f, userData.getFavourites());
    }
}
