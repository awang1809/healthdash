package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

// Tests the MetricCalculator class
public class MetricCalculatorTest {
    MetricCalculator testCalculator;
    Item itemA;
    Item itemB;
    Item itemC;
    Item itemD;
    Item itemE;
    Item itemF;
    Restaurant restaurantA;
    Restaurant restaurantB;
    RestaurantSelector selector;
    
    @BeforeEach
    void runBefore() {
        itemA = new Item("A", 100.0, 0.1, 0.1, 10, 60.0, 200, 10.0);
        itemB = new Item("B", 200.0, 0.1, 0.1, 10, 50.0, 200, 10.0);
        itemC = new Item("C", 300.0, 0.1, 0.1, 10, 40.0, 200, 10.0);
        itemD = new Item("D", 400.0, 0.1, 0.1, 10, 30.0, 200, 10.0);
        itemE = new Item("E", 500.0, 0.1, 0.1, 10, 20.0, 200, 10.0);
        itemF = new Item("F", 600.0, 0.1, 0.1, 10, 10.0, 200, 10.0);
        ArrayList<Item> itemListA = new ArrayList<>();
        ArrayList<Item> itemListB = new ArrayList<>();
        itemListA.add(itemA);
        itemListA.add(itemB);
        itemListA.add(itemC);
        itemListB.add(itemD);
        itemListB.add(itemE);
        itemListB.add(itemF);
        restaurantA = new Restaurant("A", itemListA);
        restaurantB = new Restaurant("B", itemListB);
        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        restaurantList.add(restaurantA);
        restaurantList.add(restaurantB);
        selector = new RestaurantSelector(restaurantList);
        testCalculator = new MetricCalculator(selector);
    }

    @Test
    void testConstructor() {
        assertEquals(selector, testCalculator.getSelector());
        assertNull(testCalculator.getMetric());
    }

    @Test
    void testCalculateTopItemsCalories() {
        testCalculator.setMetric("caloriesoverprice");
        selector.selectRestaurant(restaurantA);
        selector.selectRestaurant(restaurantB);
        ArrayList<Item> results = new ArrayList<>();
        results.add(itemF);
        results.add(itemE);
        results.add(itemD);
        results.add(itemC);
        results.add(itemB);
        assertEquals(results, testCalculator.calculateTopItems());
    }

    @Test
    void testCalculateTopItemsProtein() {
        testCalculator.setMetric("proteinoverprice");
        selector.selectRestaurant(restaurantA);
        selector.selectRestaurant(restaurantB);
        ArrayList<Item> results = new ArrayList<>();
        results.add(itemA);
        results.add(itemB);
        results.add(itemC);
        results.add(itemD);
        results.add(itemE);
        assertEquals(results, testCalculator.calculateTopItems());
    }

    @Test
    void testCalculateTopItemsScoreProtein() {
        testCalculator.setMetric("proteinoverprice");
        selector.selectRestaurant(restaurantA);
        selector.selectRestaurant(restaurantB);
        ArrayList<Double> scores = new ArrayList<>();
        scores.add(6.0);
        scores.add(5.0);
        scores.add(4.0);
        scores.add(3.0);
        scores.add(2.0);
        assertEquals(scores, testCalculator.calculateTopItemsScore());
    }

    @Test
    void testCalculateTopItemsScoreCalories() {
        testCalculator.setMetric("caloriesoverprice");
        selector.selectRestaurant(restaurantA);
        selector.selectRestaurant(restaurantB);
        ArrayList<Double> scores = new ArrayList<>();
        scores.add(60.0);
        scores.add(50.0);
        scores.add(40.0);
        scores.add(30.0);
        scores.add(20.0);
        assertEquals(scores, testCalculator.calculateTopItemsScore());
    }
}
