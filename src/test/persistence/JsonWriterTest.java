package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import model.UserData;

// Tests the JsonWriter class
public class JsonWriterTest extends JsonTest {
    String metric;
    ArrayList<String> selected;
    ArrayList<String> unselected; 
    ArrayList<String> favourites; 
    UserData testUserData;

    @BeforeEach
    void runBefore() {
        metric = "test";
        selected = new ArrayList<>();
        unselected = new ArrayList<>();
        favourites = new ArrayList<>();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterNoUnselectedNoFavourites() {
        try {
            UserData u = new UserData(metric, selected, unselected, favourites);
            JsonWriter writer = new JsonWriter("./data/testWriterNoUnselectedNoFavourites.json");
            writer.open();
            writer.write(u);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNoUnselectedNoFavourites.json");
            UserData userData = reader.read();
            assertEquals("test", userData.getMetric());
            assertEquals(0, userData.getSelectedRestaurants().size());
            assertEquals(0, userData.getUnselectedRestaurants().size());
            assertEquals(0, userData.getFavourites().size());
        } catch (IOException e) {
            fail("file should've been found");
        }
    }

    @Test
    void testWriterOneSelectedFavourites() {
        try {
            selected.add("mcdonalds");
            unselected.add("aw");
            favourites.add("Big Mac");
            favourites.add("Apple Turnover");
            UserData u = new UserData(metric, selected, unselected, favourites);
            JsonWriter writer = new JsonWriter("./data/testWriterOneSelectedFavourites.json");
            writer.open();
            writer.write(u);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterOneSelectedFavourites.json");
            UserData userData = reader.read();
            assertEquals("test", userData.getMetric());
            assertEquals(1, userData.getSelectedRestaurants().size());
            assertEquals(1, userData.getUnselectedRestaurants().size());
            assertEquals(2, userData.getFavourites().size());
            assertEquals("mcdonalds", userData.getSelectedRestaurants().get(0));
            assertEquals("aw", userData.getUnselectedRestaurants().get(0));
            assertEquals("Big Mac", userData.getFavourites().get(0));
            assertEquals("Apple Turnover", userData.getFavourites().get(1));
        } catch (IOException e) {
            fail("file should've been found");
        }
    }
}
