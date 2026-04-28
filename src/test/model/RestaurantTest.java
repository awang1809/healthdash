package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

// Tests the Restaurant class
public class RestaurantTest {
    Item itemA;
    Item itemB;
    Item itemC;
    ArrayList<Item> itemList;
    Restaurant testRestaurant;
    
    @BeforeEach
    void runBefore() {
        itemA = new Item("A",1.0,0.1,0.1,1,1.0,1,1.0);
        itemB = new Item("B",2.0,0.1,0.1,2,2.0,2,2.0);
        itemC = new Item("C",3.0,0.1,0.1,3,3.0,3,3.0);
        itemList = new ArrayList<Item>();
        itemList.add(itemA);
        itemList.add(itemB);
        itemList.add(itemC);
        testRestaurant = new Restaurant("A", itemList);
    }

    @Test
    void testConstuctor() {
        assertEquals("A", testRestaurant.getName());
        assertEquals(itemList, testRestaurant.getItems());
    }
}
