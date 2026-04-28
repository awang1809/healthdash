package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the graph menu, with done button
public class CalculatorMenu extends JFrame implements ActionListener {
    private JButton done;
    private JPanel graph;
    private NutritionApp app;

    // EFFECTS: creates instance of a calculator menu
    @SuppressWarnings("methodlength")
    public CalculatorMenu(NutritionApp app) {
        super("Nutrition Calculator App");
        this.app = app;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        done = new JButton("Done");
        done.setBounds(440, 250, 150, 50);
        this.add(done);

        done.addActionListener(this);


        graph = new CalculatorGraph(app);
        graph.setBounds(10,10,550,550);
        this.add(graph);

        this.setSize(600, 600);
        this.setLayout(null);
        this.setVisible(true);
    }

    // EFFECTS: performs actions, opens other windows based on which button is clicked
    @Override
    public void actionPerformed(ActionEvent action) {
        if (action.getSource() == done) {
            this.dispose();
            new AppMenu(app);
        } 
    }
}
