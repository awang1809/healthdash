package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests the FavouriteItems class
public class FavouriteItemsTest {
    Item itemA;
    Item itemB;
    FavouriteItems favouritesTest;
    
    @BeforeEach
    void runBefore() {
        itemA = new Item("A",1.0,0.1,0.1,1,1.0,1,1.0);
        itemB = new Item("B",2.0,0.1,0.1,2,2.0,2,2.0);
        favouritesTest = new FavouriteItems();
    }

    @Test
    void testConstuctor() {
        assertTrue(favouritesTest.getFavourites().isEmpty());
    }

    @Test
    void testAddItemTwoDifferent() {
        favouritesTest.addItem(itemA);
        assertEquals(itemA, favouritesTest.getFavourites().get(0));
        favouritesTest.addItem(itemB);
        assertEquals(itemB, favouritesTest.getFavourites().get(1));
        assertEquals(2, favouritesTest.getFavourites().size());
    }

    @Test
    void testAddItemDuplicate() {
        favouritesTest.addItem(itemB);
        assertEquals(itemB, favouritesTest.getFavourites().get(0));
        favouritesTest.addItem(itemB);
        assertEquals(1, favouritesTest.getFavourites().size());
    }
}

