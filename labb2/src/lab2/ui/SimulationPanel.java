package lab2.ui;

import lab2.Village;

import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel {
    public static final int BORDER = 20;

    private Village village;

    public SimulationPanel() {
        setPreferredSize(new Dimension(600, 600));
        newSimulation();
    }

    private void newSimulation() {
        //village = new Village();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawVillage(g);
        drawPeople(g);
    }

    private void drawVillage(Graphics2D g) {
        g.setColor(new Color(96, 96, 96));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawPeople(Graphics2D g) {

    }
}
