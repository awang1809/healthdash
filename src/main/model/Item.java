package model;

// Represents information for a single menu item
public class Item {
    private String name;
    private Double calories;
    private Double satfat;
    private Double transfat;
    private int sugar;
    private int sodium;
    private Double protein;
    private Double price;
    
    // EFFECTS: constructs a menu item with name, price, calories, saturated fat, trans fat, sugar, sodium and protein
    public Item(String name,Double cal,Double sat,Double trans,int sug,Double protein,int sodium,Double price) {
        this.name = name;
        this.calories = cal;
        this.satfat = sat;
        this.transfat = trans;
        this.sugar = sug;
        this.sodium = sodium;
        this.protein = protein;
        this.price = price;
    }

    // REQUIRES: metric is one of: caloriesoverprice, proteinoverprice
    // EFFECTS: returns a metric calculation for this item based on the metric argument
    public Double getMetricValue(String metric) {
        if (metric.equals("caloriesoverprice")) {
            return calories / price;
        } else if (metric.equals("proteinoverprice")) {
            return protein / price;
        } else {
            return -1.0;
        }
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Double getCalories() {
        return calories;
    }

    public Double getSatFat() {
        return satfat;
    }

    public Double getTransFat() {
        return transfat;
    }

    public int getSugar() {
        return sugar;
    }

    public int getSodium() {
        return sodium;
    }

    public Double getProtein() {
        return protein;
    }
}