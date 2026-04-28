package model;

import java.util.ArrayList;
import java.util.Comparator;

// Calculates top menu items with the given metric and selected restaurants
public class MetricCalculator {
    private RestaurantSelector selector;
    private String metric;

    // EFFECTS: creates an instance of this class with given selector
    public MetricCalculator(RestaurantSelector selector) {
        this.selector = selector;
        this.metric = null;
    }

    // REQUIRES: List of selected restaurants is not empty, metric is one of: caloriesoverprice, proteinoverprice
    // EFFECTS: Calculates the top five items that are offered at selected restaurants according to the given metric
    public ArrayList<Item> calculateTopItems() {
        EventLog.getInstance().logEvent(new Event("Calculated top items and displayed graph."));
        ArrayList<Item> allItems = new ArrayList<>();
        for (Restaurant r : selector.getSelected()) {
            for (Item i : r.getItems()) {
                allItems.add(i);
            }
        }
        Comparator<Item> comparatorByMetric = Comparator.comparingDouble(item -> item.getMetricValue(metric));
        allItems.sort(comparatorByMetric.reversed());
        ArrayList<Item> topItems = new ArrayList<>(allItems.subList(0, 5));
        return topItems;
    }

    // REQUIRES: List of selected restaurants is not empty, metric is one of: caloriesoverprice, proteinoverprice
    // EFFECTS: Calculates the scores of the top five items for given metric
    public ArrayList<Double> calculateTopItemsScore() {
        ArrayList<Item> allItems = new ArrayList<>();
        ArrayList<Double> scores = new ArrayList<>();
        for (Restaurant r : selector.getSelected()) {
            for (Item i : r.getItems()) {
                allItems.add(i);
            }
        }
        Comparator<Item> comparatorByMetric = Comparator.comparingDouble(item -> item.getMetricValue(metric));
        allItems.sort(comparatorByMetric.reversed());
        ArrayList<Item> topItems = new ArrayList<>(allItems.subList(0, 5));
        for (Item i : topItems) {
            Double value = i.getMetricValue(metric);
            scores.add(value);
        }
        return scores;
    }

    public RestaurantSelector getSelector() {
        return selector;
    }

    public void setselector(RestaurantSelector selector) {
        this.selector = selector;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
        EventLog.getInstance().logEvent(new Event("Metric set to " + metric));   
    }
}
