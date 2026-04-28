package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a selector menu
public class SelectorMenu extends JFrame implements ActionListener {
    private NutritionApp app;
    private JTextArea instructions;
    private JCheckBox mcdonalds;
    private JCheckBox aw;

    // EFFECTS: creates a new selector menu instance
    @SuppressWarnings("methodlength")
    public SelectorMenu(NutritionApp app) {
        super("Nutrition Calculator App");
        this.app = app;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        instructions = new JTextArea(50, 30);
        instructions.setEditable(false);
        instructions.append("Choose the restaurants you would like to include:");
        instructions.setBounds(50, 50, 400, 100);
        this.add(instructions);

        mcdonalds = new JCheckBox();
        mcdonalds.setText("Mcdonalds");
        mcdonalds.setFocusable(false);
        mcdonalds.setBounds(50, 250, 150, 50);
        this.add(mcdonalds);
        if (app.getSelector().isSelected("mcdonalds")) {
            mcdonalds.setSelected(true);
        }

        aw = new JCheckBox();
        aw.setText("A&W");
        aw.setFocusable(false);
        aw.setBounds(50, 300, 150, 50);
        this.add(aw);
        if (app.getSelector().isSelected("aw")) {
            aw.setSelected(true);
        }

        JButton submit = new JButton("Done");
        submit.setActionCommand("submit");
        submit.addActionListener(this);
        submit.setBounds(50, 350, 150, 50);
        this.add(submit);

        this.setSize(500, 500);
        this.setLayout(null);
        this.setVisible(true);
    }

    // EFFECTS: performs actions, opens other windows based on which button is clicked
    @Override
    public void actionPerformed(ActionEvent action) {
        if (action.getActionCommand().equals("submit")) {
            if (mcdonalds.isSelected()) {
                app.getSelector().selectRestaurant("mcdonalds");
            } else {
                app.getSelector().unselectRestaurant("mcdonalds");
            }
            if (aw.isSelected()) {
                app.getSelector().selectRestaurant("aw");
            } else {
                app.getSelector().unselectRestaurant("aw");
            }
            this.dispose();
            new AppMenu(app);
        } 
    }
}

