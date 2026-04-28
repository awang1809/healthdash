package ui;

import javax.swing.*;

import model.EventLog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

// Represents the main menu of the GUI
public class AppMenu extends JFrame implements ActionListener, WindowListener {
    private JTextArea mainPageInstructions;
    private NutritionApp app;

    // EFFECTS: creates instance of the main menu 
    @SuppressWarnings("methodlength")
    public AppMenu(NutritionApp app) {
        super("Nutrition Calculator App");
        this.app = app;
        addWindowListener(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPageInstructions = new JTextArea(50, 30);
        mainPageInstructions.setEditable(false);
        mainPageInstructions.append("Welcome to the nutrition calculator app! \n");
        mainPageInstructions.append("To start, please select the restaurants you are interested in ordering from. \n");
        mainPageInstructions
                .append("Then, select a metric you would like to use to get recommendations \n on what to eat.");
        mainPageInstructions.setBounds(50, 50, 400, 100);
        this.add(mainPageInstructions);

        JButton restaurantSelectButton = new JButton("Select Restaurants");
        restaurantSelectButton.setBounds(50, 250, 150, 50);
        this.add(restaurantSelectButton);

        restaurantSelectButton.setActionCommand("selector");
        restaurantSelectButton.addActionListener(this);

        JButton metricSelectButton = new JButton("Select Metrics");
        metricSelectButton.setBounds(210, 250, 150, 50);
        this.add(metricSelectButton);

        metricSelectButton.setActionCommand("metric");
        metricSelectButton.addActionListener(this);

        JButton calculate = new JButton("Calculate");
        calculate.setBounds(50, 310, 150, 50);
        this.add(calculate);

        calculate.setActionCommand("calculate");
        calculate.addActionListener(this);

        JButton itemViewer = new JButton("Favourite Items");
        itemViewer.setBounds(210, 310, 150, 50);
        this.add(itemViewer);

        itemViewer.setActionCommand("viewer");
        itemViewer.addActionListener(this);

        JButton save = new JButton("Save Data");
        save.setBounds(50, 370, 150, 50);
        this.add(save);

        save.setActionCommand("save");
        save.addActionListener(this);

        JButton load = new JButton("Load Data");
        load.setBounds(210, 370, 150, 50);
        this.add(load);

        load.setActionCommand("load");
        load.addActionListener(this);

        this.setSize(500, 500);
        this.setLayout(null);
        this.setVisible(true);
    }

    // EFFECTS: performs actions, opens other windows based on which button is clicked
    @Override
    @SuppressWarnings("methodlength")
    public void actionPerformed(ActionEvent action) {
        if (action.getActionCommand().equals("selector")) {
            this.dispose();
            new SelectorMenu(app);
        } else if (action.getActionCommand().equals("metric")) {
            this.dispose();
            new MetricMenu(app);
        } else if (action.getActionCommand().equals("calculate")) {
            if (app.getMetric() == "") {
                JOptionPane.showMessageDialog(null, 
                        "No metric selected.", "", JOptionPane.ERROR_MESSAGE);
            } else if (app.getSelector().getSelected().isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                        "No restaurants selected.", "", JOptionPane.ERROR_MESSAGE);
            } else {
                new CalculatorMenu(app);
            }
        } else if (action.getActionCommand().equals("viewer")) {
            this.dispose();
            new FavouritesMenu(app);
        } else if (action.getActionCommand().equals("save")) {
            app.saveUserData();
            JOptionPane.showMessageDialog(null, "Saved data.");
        } else if (action.getActionCommand().equals("load")) {
            app.loadUserData();
            JOptionPane.showMessageDialog(null, "Loaded saved data.");
        }
    }

    // EFFECTS: Prints the log to console after the window is closed
    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("Event log:");
        LogPrinter lp = new LogPrinter(EventLog.getInstance());
        lp.printLog();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // no effect
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // no effect
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // no effect
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // no effect
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // no effect
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // no effect
    }

}
