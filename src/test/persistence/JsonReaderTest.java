package persistence;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import model.UserData;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Tests the JsonReader class
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/notFound.json");
        try {
            reader.read();
            fail("expected an exception");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderNoUnselectedNoFavourites() {
        JsonReader reader = new JsonReader("./data/testReaderNoUnselectedNoFavourites.json");
        try {
            UserData u = reader.read();
            assertEquals("test", u.getMetric());
            assertEquals("mcdonalds", u.getSelectedRestaurants().get(0));
            assertEquals("aw", u.getSelectedRestaurants().get(1));
            assertTrue(u.getUnselectedRestaurants().isEmpty());
            assertTrue(u.getFavourites().isEmpty());
        } catch (IOException e) {
            fail("file should've been found");
        }
    }

    @Test
    void testReaderOneSelectedFavourites() {
        JsonReader reader = new JsonReader("./data/testReaderOneSelectedFavourites.json");
        try {
            UserData u = reader.read();
            assertEquals("test", u.getMetric());
            assertEquals("mcdonalds", u.getSelectedRestaurants().get(0));
            assertEquals("aw", u.getUnselectedRestaurants().get(0));
            assertEquals("Big Mac", u.getFavourites().get(0));
            assertEquals("Apple Turnover", u.getFavourites().get(1));
        } catch (IOException e) {
            fail("file should've been found");
        }
    }
}
