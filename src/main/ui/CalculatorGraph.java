package ui;

import java.awt.*;
import javax.swing.*;

import model.Item;
import model.MetricCalculator;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

// Represents a JPanel of a graph with calculated top items and their metric values
public class CalculatorGraph extends JPanel {
    private MetricCalculator calculator;
    private ArrayList<Item> topItems;
    private ArrayList<Double> scores;
    
    // EFFECTS: creates instance of a calculator graph
    @SuppressWarnings("methodlength")
    public CalculatorGraph(NutritionApp app) {
        calculator = app.getCalculator();
        topItems = calculator.calculateTopItems();
        scores = calculator.calculateTopItemsScore();

        this.setPreferredSize(new Dimension(500, 500));
        this.setVisible(true);
    }

    // EFFECTS: draws the graph based on top item and metric info
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int gap = 50;
        Double scaleFactor = 400 / scores.get(0);
        String highScore = String.valueOf(Math.round(scores.get(0)));
        String lowScore = String.valueOf(Math.round(scores.get(4)));
        int coordUpperY = Math.toIntExact(500 - Math.round(scores.get(0) * scaleFactor));
        int coordLowerY = Math.toIntExact(500 - Math.round(scores.get(4) * scaleFactor));
        g2.drawLine(gap, gap, gap, 500 - gap);
        g2.drawLine(gap, 500 - gap, 500 - gap, 500 - gap);
        g2.drawLine(40, coordUpperY, 50, coordUpperY);
        g2.drawLine(40, coordLowerY, 50, coordLowerY);
        g2.drawString(highScore, 20, 500 - Math.round(scores.get(0) * scaleFactor));
        g2.drawString(lowScore, 20, 500 - Math.round(scores.get(4) * scaleFactor));
        for (int i = 0; i < 5; i++) {
            Double barHeight = scores.get(i);
            Double top = 500 - barHeight * scaleFactor;
            AffineTransform original = g2.getTransform();
            g2.fill(new Rectangle2D.Double(i * gap + i * 25 + gap, top, 50, -(top - 500 + gap)));
            g2.rotate(Math.PI / 8, i * gap + i * 25 + gap, 500 - gap + 10);
            g2.drawString(topItems.get(i).getName(), i * gap + i * 25 + gap, 500 - gap + 10);
            g2.setTransform(original);
        }
    }
}
