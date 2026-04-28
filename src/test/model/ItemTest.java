package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests the Item class
public class ItemTest {
    Item testItem;
    
    @BeforeEach
    void runBefore() {
        testItem = new Item("A",1.0,0.1,0.1,4,5.0,6,2.0);
    }

    @Test
    void testConstuctor() {
        assertEquals("A", testItem.getName());
        assertEquals(1, testItem.getCalories());
        assertEquals(0.1, testItem.getSatFat());
        assertEquals(0.1, testItem.getTransFat());
        assertEquals(4, testItem.getSugar());
        assertEquals(6, testItem.getSodium());
        assertEquals(5.0, testItem.getProtein());
        assertEquals(2, testItem.getPrice());
    }

    @Test 
    void testGetMetricValueCalories() {
        assertEquals(0.5, testItem.getMetricValue("caloriesoverprice"));
    }

    @Test
    void testGetMetricValueProtein() {
        assertEquals(2.5, testItem.getMetricValue("proteinoverprice"));
    }

    @Test
    void testGetMetricValueInvalid() {
        assertEquals(-1, testItem.getMetricValue("a"));
    }
}
