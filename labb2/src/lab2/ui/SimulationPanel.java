package lab2.ui;

import lab2.Village;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import lab2.Person;

public class SimulationPanel extends JPanel implements ActionListener {
    public static final int BORDER = 20;
    
    private SimulationUI ui;

    private int day;
    private Timer timer;
    private Village village;

    public SimulationPanel(SimulationUI ui) {
        this.ui = ui;

        setPreferredSize(new Dimension(600, 600));
    }

    public void initialize(int simulationSpeed, int population, int width, int height, boolean vaccinated, double initSickProb, double getWellProb, double deadProb, double infectProb, int infectRange, int daysImmune) {
        day = 1;
        timer = new Timer(simulationSpeed, this);
        village = new Village(population, width, height, vaccinated, initSickProb, getWellProb, deadProb, infectProb, infectRange, daysImmune);
        
        ui.outputText.setText("");
        
        repaint();
    }
    
    public void start() {
        timer.start();
    }
    
    public void stop() {
        timer.stop();
        
        ui.simulateButton.setText("Initialize village!");
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
        
        if (village == null)
            return;

    }

    private void drawPeople(Graphics2D g) {
        if (village == null)
            return;

        for (Person person : village.getPopulation())
            drawPerson(g, person);
    }

    private void drawPerson(Graphics2D g, Person person) {
        if (person.isSick())
            g.setColor(new Color(214, 198, 191));
        else if (person.isImmune())
            g.setColor(new Color(65, 109, 204));
        else if (person.isDead())
            g.setColor(new Color(204, 68, 44));
        else
            g.setColor(new Color(85, 204, 91));
        
        int x = getBounds().x + (int) (getWidth() * (person.getX() / village.width));
        int y = getBounds().y + (int) (getHeight() * (person.getY() / village.height));
        
        int diameter = getDiameter();
        
        g.fillOval(x, y, diameter, diameter);
    }
    
    private int getDiameter() {
        if (village.width <= 500)
            return 8;
        else if (village.width <= 1000)
            return 6;
        else if (village.width < 5000)
            return 4;
        else
            return 3;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ui.outputText.append(String.format("Day %3d: sick=%4d, dead=%4d, immune=%4d\n", day, village.countSick(), village.countDead(), village.countImmune()));

        if (village.countSick() > 0 || village.countImmune() > 0) {
            village.nextDay();
            day++;
            repaint();
        } else {
            stop();
        }
    }
}
