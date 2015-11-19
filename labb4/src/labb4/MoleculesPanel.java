package labb4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * ****************************************************************************************
 * Table
 *
 * The table has some constants and instance variables relating to the graphics and
 * the balls. When simulating the molecules it starts a timer
 * which fires UPDATE_FREQUENCY times per second. Each time the timer is
 * activated one step of the simulation is performed. The table reacts to
 * events to accomplish repaints and to stop or start the timer.
 *
 */
class MoleculesPanel extends JPanel implements MouseListener, ActionListener {
    public static int BALL_COUNT = 500;
    private static double FRICTION = 0;
    private static double RADIUS = 7;

    public final int   WIDTH          = 900;
    public final int   HEIGHT         = 600;
    private final int   WALL_THICKNESS = 20;
    private final Color COLOR          = Color.green;
    private final Color WALL_COLOR     = Color.black;
    private final Timer simulationTimer;

    public final Rectangle innerBounds;
    private final Molecule[] molecules;

    MoleculesPanel() {
        innerBounds = new Rectangle(getBounds().x + WALL_THICKNESS, getBounds().y + WALL_THICKNESS, WIDTH, HEIGHT);

        setPreferredSize(new Dimension(WIDTH + 2 * WALL_THICKNESS,
                HEIGHT + 2 * WALL_THICKNESS));

        molecules = new Molecule[BALL_COUNT];
        createInitialBalls();

        addMouseListener(this);

        simulationTimer = new Timer((int) (1000.0 / DiseaseSimulator.UPDATE_FREQUENCY), this);
        simulationTimer.start();
    }

    public void start() {
        simulationTimer.start();
    }

    public void pause() {
        simulationTimer.stop();
    }

    public int countDead() {
        int deadCount = 0;
        for (Molecule molecule : molecules) {
            if (molecule.isDead()) {
                deadCount++;
            }
        }

        return deadCount;
    }

    private void createInitialBalls(){
        for (int i = 0; i < molecules.length; i++) {
            molecules[i] = createRandomMolecule(i);
        }
    }

    private Molecule createRandomMolecule(int index) {
        int radius = 15;

        double width = WIDTH - 2 * radius;
        double height = HEIGHT - 2 * radius;

        Molecule molecule = null;

        boolean foundCollision = true;

        double interval = 3.5;

        while (foundCollision) {
            foundCollision = false;

            double x = innerBounds.x + radius + Math.random() * (width / 2.0);
            double y = innerBounds.x + radius + Math.random() * height;

            Coord position = new Coord(x, y);
            Coord velocity = new Coord(interval * Math.random() - interval/2, interval * Math.random() - interval/2);

            molecule = new Molecule(innerBounds, position, velocity, FRICTION, RADIUS, Math.random() < Molecule.INIT_INFECTED_PROB);

            for (int i = 0; i < index; i++) {
                if (molecule.getBounds().intersects(molecules[i].getBounds())) {
                    foundCollision = true;
                    break;
                }
            }
        }

        return molecule;
    }

    public void actionPerformed(ActionEvent e) {          // Timer event
        for (Ball ball : molecules) {
            if (ball.isVisible()) {
                ball.move();
            }
        }

        for (Ball ball : molecules) {
            if (ball.isVisible()) {
                ball.checkCollisions(molecules);
            }
        }

        repaint();

        boolean hasMovingBalls = false;
        for (Ball ball : molecules) {
            if (ball.isVisible() && ball.isMoving()) {
                hasMovingBalls = true;
            }
        }

        if (!hasMovingBalls) {
            simulationTimer.stop();
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2D = (Graphics2D) graphics;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // This makes the graphics smoother
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.setColor(WALL_COLOR);
        g2D.fillRect(0, 0, WIDTH + 2 * WALL_THICKNESS, HEIGHT + 2 * WALL_THICKNESS);

        g2D.setColor(COLOR);
        g2D.fillRect(WALL_THICKNESS, WALL_THICKNESS, WIDTH, HEIGHT);

        for (Ball ball : molecules) {
            if (ball.isVisible()) {
                ball.paint(g2D);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!simulationTimer.isRunning()) {
            Coord position = new Coord(e);

            for (Molecule molecule : molecules) {
                double distance = Coord.distance(position, molecule.position);

                if (distance < molecule.radius) {
                    molecule.becomeSick();
                    repaint();
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}  // end class Table
