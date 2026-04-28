package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

// Tests the RestaurantSelector class
public class RestaurantSelectorTest {
    Restaurant restaurantA;
    Restaurant restaurantB;
    RestaurantSelector testSelector;
    ArrayList<Item> emptyList;
    ArrayList<Restaurant> restaurantListUnselected;
    ArrayList<Restaurant> restaurantListSelected;
    
    @BeforeEach
    void runBefore() {
        emptyList = new ArrayList<Item>();
        restaurantA = new Restaurant("A", emptyList);
        restaurantB = new Restaurant("B", emptyList);
        restaurantListUnselected = new ArrayList<>();
        restaurantListUnselected.add(restaurantA);
        restaurantListUnselected.add(restaurantB);
        testSelector = new RestaurantSelector(restaurantListUnselected);
        restaurantListSelected = new ArrayList<>();
    }

    @Test
    void testConstuctor() {
        assertEquals(restaurantListSelected, testSelector.getSelected());
        assertEquals(restaurantListUnselected,testSelector.getUnselected());
    }

    @Test
    void testSelectRestaurantNotSelected() {
        testSelector.selectRestaurant(restaurantA);
        restaurantListSelected.add(restaurantA);
        restaurantListUnselected.remove(restaurantA);
        assertEquals(restaurantListSelected, testSelector.getSelected());
        assertEquals(restaurantListUnselected, testSelector.getUnselected());
        testSelector.selectRestaurant(restaurantB);
        restaurantListSelected.add(restaurantB);
        restaurantListUnselected.remove(restaurantB);
        assertEquals(restaurantListSelected, testSelector.getSelected());
        assertEquals(restaurantListUnselected, testSelector.getUnselected());
    }

    @Test
    void testSelectRestaurantAlreadySelected() {
        testSelector.selectRestaurant(restaurantA);
        restaurantListSelected.add(restaurantA);
        restaurantListUnselected.remove(restaurantA);
        assertEquals(restaurantListSelected, testSelector.getSelected());
        assertEquals(restaurantListUnselected, testSelector.getUnselected());
        testSelector.selectRestaurant(restaurantA);
        assertEquals(restaurantListSelected, testSelector.getSelected());
        assertEquals(restaurantListUnselected, testSelector.getUnselected());
    }

    @Test
    void testUnselectRestaurantNotUnselected() {
        testSelector.selectRestaurant(restaurantA);
        testSelector.selectRestaurant(restaurantB);
        testSelector.unselectRestaurant(restaurantA);
        restaurantListSelected.add(restaurantB);
        restaurantListUnselected.remove(restaurantB);
        assertEquals(restaurantListSelected, testSelector.getSelected());
        assertEquals(restaurantListUnselected, testSelector.getUnselected());
    }

    @Test
    void testUnselectedRestaurantAlreadyUnselected() {
        testSelector.unselectRestaurant(restaurantA);
        assertEquals(restaurantListSelected, testSelector.getSelected());
        assertEquals(restaurantListUnselected, testSelector.getUnselected());
    }

    @Test
    void testSelectRestaurantNotSelectedString() {
        testSelector.selectRestaurant("A");
        restaurantListSelected.add(restaurantA);
        restaurantListUnselected.remove(restaurantA);
        assertEquals(restaurantListSelected, testSelector.getSelected());
        assertEquals(restaurantListUnselected, testSelector.getUnselected());
        testSelector.selectRestaurant("B");
        restaurantListSelected.add(restaurantB);
        restaurantListUnselected.remove(restaurantB);
        assertEquals(restaurantListSelected, testSelector.getSelected());
        assertEquals(restaurantListUnselected, testSelector.getUnselected());
    }

    @Test
    void testSelectRestaurantAlreadySelectedString() {
        testSelector.selectRestaurant("A");
        restaurantListSelected.add(restaurantA);
        restaurantListUnselected.remove(restaurantA);
        assertEquals(restaurantListSelected, testSelector.getSelected());
        assertEquals(restaurantListUnselected, testSelector.getUnselected());
        testSelector.selectRestaurant("A");
        assertEquals(restaurantListSelected, testSelector.getSelected());
        assertEquals(restaurantListUnselected, testSelector.getUnselected());
    }

    @Test
    void testUnselectRestaurantNotUnselectedString() {
        testSelector.selectRestaurant("A");
        testSelector.selectRestaurant("B");
        testSelector.unselectRestaurant("A");
        restaurantListSelected.add(restaurantB);
        restaurantListUnselected.remove(restaurantB);
        assertEquals(restaurantListSelected, testSelector.getSelected());
        assertEquals(restaurantListUnselected, testSelector.getUnselected());
    }

    @Test
    void testUnselectedRestaurantAlreadyUnselectedString() {
        testSelector.unselectRestaurant("A");
        assertEquals(restaurantListSelected, testSelector.getSelected());
        assertEquals(restaurantListUnselected, testSelector.getUnselected());
    }

    @Test
    void testIsSelected() {
        testSelector.selectRestaurant(restaurantA);
        assertTrue(testSelector.isSelected("A"));
        assertFalse(testSelector.isSelected("B"));
    }
}
