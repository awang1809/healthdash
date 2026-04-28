package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the metric menu
public class MetricMenu extends JFrame implements ActionListener {
    private JTextArea instructions;
    private JComboBox<String> metricOptions;
    private JButton done;
    private NutritionApp app;

    // EFFECTS: creates instance of a metric menu
    @SuppressWarnings("methodlength")
    public MetricMenu(NutritionApp app) {
        super("Nutrition Calculator App");
        this.app = app;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] options = {"Calories per dollar", "Protein per dollar"};
        metricOptions = new JComboBox<>(options);

        if (app.getMetric().equals("")) {
            metricOptions.insertItemAt("...", 0);
            metricOptions.setSelectedIndex(0);
        } else if (app.getMetric().equals("proteinoverprice")) {
            metricOptions.setSelectedIndex(1);
        }
        metricOptions.setBounds(50, 150, 150, 30);
        this.add(metricOptions);

        done = new JButton("Done");
        done.setBounds(50, 300, 150, 50);
        this.add(done);

        done.addActionListener(this);

        instructions = new JTextArea(50, 30);
        instructions.setEditable(false);
        instructions.append("Choose the metric you would like to calculate by:");
        instructions.setBounds(50, 50, 400, 50);
        this.add(instructions);

        this.setSize(500, 500);
        this.setLayout(null);
        this.setVisible(true);
    }

    // EFFECTS: performs actions, opens other windows based on which button is clicked
    @Override
    public void actionPerformed(ActionEvent action) {
        if (action.getSource() == done) {
            String metric = (String) metricOptions.getSelectedItem();
            if (metric.equals("Calories per dollar")) {
                app.setMetric("caloriesoverprice");
            } else if (metric.equals("Protein per dollar")) {
                app.setMetric("proteinoverprice");
            }
            this.dispose();
            new AppMenu(app);
        }
    }
}
