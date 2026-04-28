package ui;

import java.io.*;
import java.util.*;

import model.Item;
import model.MetricCalculator;
import model.Restaurant;
import model.RestaurantSelector;
import model.FavouriteItems;
import model.UserData;

import persistence.*;

// main ui of app
public class NutritionApp {
    private boolean keepGoing;
    private Scanner input;
    private Restaurant aw;
    private Restaurant mcdonalds;
    private ArrayList<Restaurant> allRestaurants;
    private ArrayList<Restaurant> listOfRestaurants;
    private RestaurantSelector selector;
    private ArrayList<Item> allItems;
    private FavouriteItems favouriteItems;
    private String metric;
    private UserData userData;
    private MetricCalculator calc;
    private static final String JSON_STORE = "./data/userData.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the app
    public NutritionApp() {
        run();
    }

    // MODIFIES: this
    // EFFECTS: initializes fields, reads in data, prints instructions, handles
    // inputs
    public void run() {
        init();
        keepGoing = true;
        input = new Scanner(System.in);
        printInstructionsFirst();

        while (keepGoing) {
            printInstructionsRepeat();
            handleInputs();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes needed fields and reads data
    public void init() {
        aw = readFiles("data/aw.csv", "aw");
        mcdonalds = readFiles("data/mcdonalds.csv", "mcdonalds");
        allRestaurants = new ArrayList<>();
        allRestaurants.add(mcdonalds);
        allRestaurants.add(aw);
        listOfRestaurants = new ArrayList<>();
        listOfRestaurants.add(mcdonalds);
        listOfRestaurants.add(aw);
        selector = new RestaurantSelector(allRestaurants);
        allItems = new ArrayList<>();
        favouriteItems = new FavouriteItems();
        metric = "";
        calc = new MetricCalculator(selector);
        for (Restaurant r : allRestaurants) {
            for (Item i : r.getItems()) {
                allItems.add(i);
            }
        }
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        new AppMenu(this);
    }

    // MODIFIES: this
    // EFFECTS: reads the user input and performs corresponding action
    public void handleInputs() {
        String command = null;
        command = input.nextLine();
        if (command.equals("a")) {
            handleRestaurantSelector();
        } else if (command.equals("b")) {
            handleMetricSelector();
        } else if (command.equals("c")) {
            calculate();
        } else if (command.equals("d")) {
            itemViewer();
        } else if (command.equals("e")) {
            loadUserData();
        } else if (command.equals("f")) {
            saveUserData();
        } else {
            System.out.println("Error: not a valid command");
        }
    }

    // MODIFIES: this
    // EFFECTS: reads given csv file in the data folder, creates restaurant with csv
    // data and given name
    @SuppressWarnings("methodlength")
    public Restaurant readFiles(String path, String restaurantName) {
        ArrayList<Item> items = new ArrayList<>();
        String line = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 0) {
                    String name = parts[0];
                    Double calories = Double.parseDouble(parts[1]);
                    Double satfat = Double.parseDouble(parts[2]);
                    Double transfat = Double.parseDouble(parts[3]);
                    int sugar = Integer.parseInt(parts[4]);
                    Double protein = Double.parseDouble(parts[5]);
                    int sodium = Integer.parseInt(parts[6]);
                    Double price = Double.parseDouble(parts[7]);

                    items.add(new Item(name, calories, satfat, transfat, sugar, protein, sodium, price));
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Restaurant restaurant = new Restaurant(restaurantName, items);
        return restaurant;
    }

    // EFFECTS: prints a welcome message explaining the app once at start of
    // application
    public void printInstructionsFirst() {
        System.out.println("Welcome to the nutrition calculator App!");
        System.out.println("To start, please select the restaurants you are interested in ordering from.");
        System.out.println("Then, select a metric you would like to use to get recommendations on what to eat.");
    }

    // EFFECTS: prints commands and their corresponding actions, repeating every
    // time user returns to home
    public void printInstructionsRepeat() {
        System.out.println("Please type the letter of the action you would like to perform to proceed:");
        System.out.println("a -> select and unselect restaurants");
        System.out.println("b -> select a metric");
        System.out.println("c -> search for items based on current selection of restaurants and metric");
        System.out.println("d -> view specific item nutritional content and add items to a list o favourites");
        System.out.println("e -> load saved data for metric, selected restaurants, and favourited items");
        System.out.println("f -> save data for metric, selected and unselected restaurants, and favourited items");
    }

    // EFFECTS: handles user inputs for restaurant selector functionality
    public void handleRestaurantSelector() {
        Boolean stay = true;
        String command = null;
        while (stay) {
            selectorInfo();
            command = input.nextLine();
            if (command.equals("s")) {
                handleSelect();
            } else if (command.equals("u")) {
                handleUnselect();
            } else if (command.equals("b")) {
                stay = false;
            } else {
                System.out.println("Error: not a valid command");
            }
        }
    }

    // EFFECTS: prints info about restaurants that are currently selected or
    // unselected
    public void selectorInfo() {
        System.out.println("Would you like to select or unselect restaurants?");
        System.out.println("Currently selected:");
        for (Restaurant r : selector.getSelected()) {
            System.out.println(r.getName());
        }
        System.out.println("Currently not selected:");
        for (Restaurant r : selector.getUnselected()) {
            System.out.println(r.getName());
        }
        System.out.println("s -> select restaurants");
        System.out.println("u -> unselect restaurants");
        System.out.println("b -> back");
    }

    // MODIFIES: this
    // EFFECTS: selects restaurants in selector based on user inputs
    public void handleSelect() {
        Boolean stay = true;
        String command = null;
        while (stay) {
            selectInfo();
            command = input.nextLine();
            if (command.equals("b")) {
                stay = false;
            } else {
                for (Restaurant r : selector.getUnselected()) {
                    if (r.getName().equals(command)) {
                        selector.selectRestaurant(r);
                        break;
                    }
                }
            }
        }
    }

    // EFFECTS: information about restaurants that are currently selected or
    // unselected
    public void selectInfo() {
        System.out.println("Type the name of the restaurant you want to select, or b to go back");
        System.out.println("Currently selected:");
        for (Restaurant r : selector.getSelected()) {
            System.out.println(r.getName());
        }
        System.out.println("Currently not selected:");
        for (Restaurant r : selector.getUnselected()) {
            System.out.println(r.getName());
        }
    }

    // MODIFIES: this
    // EFFECTS: unselects restaurants in selector based on user inputs
    public void handleUnselect() {
        Boolean stay = true;
        String command = null;
        while (stay) {
            unselectInfo();
            command = input.nextLine();
            if (command.equals("b")) {
                stay = false;
            } else {
                for (Restaurant r : selector.getSelected()) {
                    if (r.getName().equals(command)) {
                        selector.unselectRestaurant(r);
                        break;
                    }
                }
            }
        }
    }

    // EFFECTS: information about restaurants that are currently selected or
    // unselected
    public void unselectInfo() {
        System.out.println("Type the name of the restaurants you would like to unselect, or b to go back");
        System.out.println("Currently selected:");
        for (Restaurant r : selector.getSelected()) {
            System.out.println(r.getName());
        }
        System.out.println("Currently not selected:");
        for (Restaurant r : selector.getUnselected()) {
            System.out.println(r.getName());
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the metric based on user input
    public void handleMetricSelector() {
        Boolean stay = true;
        String command = null;
        while (stay) {
            metricInstructions();
            command = input.nextLine();
            if (command.equals("c")) {
                metric = "caloriesoverprice";
            } else if (command.equals("p")) {
                metric = "proteinoverprice";
            } else if (command.equals("b")) {
                stay = false;
            } else {
                System.out.println("Not a valid metric!");
            }
        }
    }

    // EFFECTS: shows the current metric and gives instructions on how to pick a new
    // metric
    public void metricInstructions() {
        System.out.println("Please select a metric you would like to rank by:");
        System.out.println("Current metric:");
        if (metric == "") {
            System.out.println("None");
        } else if (metric.equals("caloriesoverprice")) {
            System.out.println("calories divided by price");
        } else if (metric.equals("proteinoverprice")) {
            System.out.println("protein divided by price");
        }
        System.out.println("c -> calories divided by price");
        System.out.println("p -> protein divided by price");
        System.out.println("b -> back");
    }

    // EFFECTS: calculates top items based on user selected restaurants and metric
    public void calculate() {
        if (metric == "") {
            System.out.println("You have not selected a metric yet!");
        } else if (selector.getSelected().size() == 0) {
            System.out.println("You have not selected any restaurants yet!");
        } else {
            calc.setselector(selector);
            ArrayList<Item> topItems = calc.calculateTopItems();
            System.out.println("The top five items for your metric are:");
            for (Item i : topItems) {
                System.out.println(i.getName());
            }
        }
    }

    // EFFECTS: allows users to find items and view their nutritional info, or add
    // items to favourites
    public void itemViewer() {
        Boolean stay = true;
        String command = null;
        while (stay) {
            viewerInstructions();
            command = input.nextLine();
            if (command.equals("a")) {
                showFavouriteItems();
            } else if (command.equals("b")) {
                stay = false;
            } else {
                findItem(command);
            }
        }
    }

    // EFFECTS: shows which items are currently favourited
    public void showFavouriteItems() {
        System.out.println("Here are your current favourited items:");
        for (Item i : favouriteItems.getFavourites()) {
            System.out.println(i.getName());
        }
    }

    // EFFECTS: finds the item and returns all relevant information about the item
    public void findItem(String command) {
        boolean foundItem = false;
        for (Item i : allItems) {
            if (i.getName().toLowerCase().contains(command.toLowerCase())) {
                System.out.println("Your item is:" + i.getName());
                System.out.println("Calories:" + i.getCalories());
                System.out.println("Saturated Fat (g):" + i.getSatFat());
                System.out.println("Trans Fat (g):" + i.getTransFat());
                System.out.println("Sugar (g):" + i.getSugar());
                System.out.println("Sodium (mg):" + i.getSodium());
                System.out.println("Protein (g):" + i.getProtein());
                System.out.println("Price (CAD):" + i.getPrice());
                askAddItem(i);
                foundItem = true;
                break;
            }
        }
        if (!foundItem) {
            System.out.println("Could not find your item!");
        }
    }

    // MODIFIES: this
    // EFFECTS: asks the user if they want to add the item to their favourites
    public void askAddItem(Item i) {
        boolean stay = true;
        String command = null;
        while (stay) {
            addItemInstructions();
            command = input.nextLine();
            if (command.equals("y")) {
                if (favouriteItems.getFavourites().contains(i)) {
                    System.out.println("Error: item already in favourites!");
                } else {
                    favouriteItems.addItem(i);
                }
                stay = false;
            } else if (command.equals("n")) {
                stay = false;
            } else {
                System.out.println("Not a valid command!");
            }
        }
    }

    // EFFECTS: instructions for user to add an item to favourites
    public void addItemInstructions() {
        System.out.println("Would you like to add this item to your favourites?");
        System.out.println("y -> yes");
        System.out.println("n -> no");
    }

    // EFFECTS: user instructions for the viewer page
    public void viewerInstructions() {
        System.out.println("Search for an item based on its name to view its nutrition or add it to favourites.");
        System.out.println("You can also view your current list of favourite items here!");
        System.out.println("a -> view favourites");
        System.out.println("b -> back");
    }

    // MODIFIES: this
    // EFFECTS: loads userdata from file
    public void loadUserData() {
        try {
            userData = jsonReader.read();
            System.out.println("Loaded saved user data from " + JSON_STORE);
            updateFields();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (Exception e) {
            System.out.println("Error: there is no saved file yet!");
        }
    }

    // MODIFIES: this
    // EFFECTS: updates fields to match values provided in userdata
    public void updateFields() {
        metric = userData.getMetric();
        ArrayList<String> selectedRestaurants = userData.getSelectedRestaurants();
        ArrayList<String> unselectedRestaurants = userData.getUnselectedRestaurants();
        ArrayList<String> favourites = userData.getFavourites();
        for (Restaurant r : new ArrayList<>(listOfRestaurants)) {
            if (selectedRestaurants.contains(r.getName()) && !selector.getSelected().contains(r)) {
                selector.selectRestaurant(r);
            } else if (unselectedRestaurants.contains(r.getName()) && !selector.getUnselected().contains(r)) {
                selector.unselectRestaurant(r);
            }
        }
        favouriteItems = new FavouriteItems();
        for (Item i : allItems) {
            if (favourites.contains(i.getName())) {
                favouriteItems.addItem(i);
            }
        }
    }

    // EFFECTS: saves the workroom to file
    public void saveUserData() {
        try {
            prepareForSave();
            jsonWriter.open();
            jsonWriter.write(userData);
            jsonWriter.close();
            System.out.println("Saved data to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: prepares a save by creating a new userdata object with current state of the program
    public void prepareForSave() {
        ArrayList<Restaurant> selected = selector.getSelected();
        ArrayList<Restaurant> unselected = selector.getUnselected();
        ArrayList<String> selectedNames = getRestaurantNames(selected);
        ArrayList<String> unselectedNames = getRestaurantNames(unselected);
        ArrayList<String> favouriteNames = new ArrayList<>();
        for (Item i : favouriteItems.getFavourites()) {
            favouriteNames.add(i.getName());
        }
        userData = new UserData(metric, selectedNames, unselectedNames, favouriteNames);
    }

    public ArrayList<String> getRestaurantNames(ArrayList<Restaurant> restaurants) {
        ArrayList<String> toReturn = new ArrayList<>();
        for (Restaurant r : restaurants) {
            toReturn.add(r.getName());
        }
        return toReturn;
    }

    public RestaurantSelector getSelector() {
        return selector;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String s) {
        this.metric = s;
        calc.setMetric(s);
    }

    // EFFECTS: finds the item and returns all relevant information about the item
    public ArrayList<String> viewItemInfo(String command) {
        ArrayList<String> info = new ArrayList<>();
        for (Item i : allItems) {
            if (i.getName().toLowerCase().contains(command.toLowerCase())) {
                info.add(i.getName());
                info.add("Calories:" + i.getCalories());
                info.add("Saturated Fat (g):" + i.getSatFat());
                info.add("Trans Fat (g):" + i.getTransFat());
                info.add("Sugar (g):" + i.getSugar());
                info.add("Sodium (mg):" + i.getSodium());
                info.add("Protein (g):" + i.getProtein());
                info.add("Price (CAD):" + i.getPrice());
                System.out.println(info);
                return info;
            }
        }
        return info;
    }

    // EFFECTS: returns found item, otherwise returns null
    public Item returnItem(String command) {
        for (Item i : allItems) {
            if (i.getName().toLowerCase().contains(command.toLowerCase())) {
                return i;
            }
        }
        return null;
    }

    public FavouriteItems getFavouriteItems() {
        return favouriteItems;
    }

    public MetricCalculator getCalculator() {
        calc.setselector(selector);
        return calc;
    }
}
