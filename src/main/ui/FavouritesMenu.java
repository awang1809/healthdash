package ui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.Item;

// Represents the favourites menu 
public class FavouritesMenu extends JFrame implements ActionListener {
    private JTextArea instructions;
    private JTextArea items;
    private JTextField itemSearcher;
    private JButton done;
    private JButton search;
    private NutritionApp app;

    // EFFECTS: creates instance of a favourites menu
    @SuppressWarnings("methodlength")
    public FavouritesMenu(NutritionApp app) {
        super("Nutrition Calculator App");
        this.app = app;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        done = new JButton("Done");
        done.setBounds(50, 360, 150, 50);
        this.add(done);
        done.addActionListener(this);

        search = new JButton("Search");
        search.setBounds(50, 300, 150, 50);
        this.add(search);
        search.addActionListener(this);

        itemSearcher = new JTextField();
        itemSearcher.setPreferredSize(new Dimension(250, 40));
        itemSearcher.setBounds(50, 200, 150, 50);
        this.add(itemSearcher);

        instructions = new JTextArea(50, 30);
        instructions.setEditable(false);
        instructions.append("Look up an item to view its nutritional information and add it to favourites:");
        instructions.setBounds(50, 50, 400, 50);
        this.add(instructions);

        items = new JTextArea(5, 3);
        items.setEditable(false);
        items.append("List of favourited items: \n");
        for (Item i : app.getFavouriteItems().getFavourites()) {
            items.append(i.getName() + "\n");
        }
        items.setBounds(210, 110, 250, 350);
        this.add(items);

        this.add(instructions);
        this.setSize(500, 500);
        this.setLayout(null);
        this.setVisible(true);
    }

    // EFFECTS: performs actions, opens other windows based on which button is clicked
    @Override
    public void actionPerformed(ActionEvent action) {
        if (action.getSource() == done) {
            this.dispose();
            new AppMenu(app);
        } else if (action.getSource() == search) {
            ArrayList<String> info = app.viewItemInfo(itemSearcher.getText());
            if (info.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No item found.", "", JOptionPane.ERROR_MESSAGE);
            } else {
                JPanel panel = makeMessage(info);
                int answer = JOptionPane.showConfirmDialog(null,
                        panel,
                        "", JOptionPane.YES_NO_OPTION);
                if (answer == 0) {
                    app.getFavouriteItems().addItem(app.returnItem(info.get(0)));
                    JOptionPane.showMessageDialog(null, "Item added.", "", JOptionPane.PLAIN_MESSAGE);
                    this.dispose();
                    new FavouritesMenu(app);
                }
            }
        }
    }

    // EFFECTS: makes panel with item information
    public JPanel makeMessage(ArrayList<String> ls) {
        JPanel panel = new JPanel();
        panel.setBounds(20, 20, 80, 10);

        JTextArea info = new JTextArea(5, 10);
        info.setEditable(false);
        for (String s : ls) {
            info.append(s + "\n");
        }
        info.append("Would you like to add this item to your favourites?");
        info.setBounds(50, 50, 80, 10);
        panel.add(info);

        return panel;
    }
}
