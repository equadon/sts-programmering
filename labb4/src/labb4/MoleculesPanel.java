package labb4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;

/**
 * ****************************************************************************************
 * Table
 *
 * The table has some constants and instance variables relating to the graphics and
 * the balls. When simulating the balls it starts a timer
 * which fires UPDATE_FREQUENCY times per second. Each time the timer is
 * activated one step of the simulation is performed. The table reacts to
 * events to accomplish repaints and to stop or start the timer.
 *
 */
class MoleculesPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    public static int BALL_COUNT = 400;
    private static double FRICTION = 0;
    private static double RADIUS = 7;

    private static double INIT_INFECTED_PROB = 0.5;

    private final int   WIDTH          = 600;
    private final int   HEIGHT         = 600;
    private final int   WALL_THICKNESS = 20;
    private final Color COLOR          = Color.green;
    private final Color WALL_COLOR     = Color.black;
    private final Timer simulationTimer;

    public final Rectangle innerBounds;
    private final Ball[] balls;

    MoleculesPanel() {
        innerBounds = new Rectangle(getBounds().x + WALL_THICKNESS, getBounds().y + WALL_THICKNESS, WIDTH, HEIGHT);

        setPreferredSize(new Dimension(WIDTH + 2 * WALL_THICKNESS,
                HEIGHT + 2 * WALL_THICKNESS));

        balls = new Ball[BALL_COUNT];
        createInitialBalls();

        addMouseListener(this);
        addMouseMotionListener(this);

        simulationTimer = new Timer((int) (1000.0 / Pool.UPDATE_FREQUENCY), this);
        simulationTimer.start();
    }

    private void createInitialBalls(){
        for (int i = 0; i < balls.length; i++) {
            balls[i] = createRandomBall(i);
        }
    }

    private Ball createRandomBall(int index) {
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

            molecule = new Molecule(innerBounds, position, velocity, FRICTION, RADIUS, Math.random() < INIT_INFECTED_PROB);

            for (int i = 0; i < index; i++) {
                if (molecule.getBounds().intersects(balls[i].getBounds())) {
                    foundCollision = true;
                    break;
                }
            }
        }

        return molecule;
    }

    public void actionPerformed(ActionEvent e) {          // Timer event
        for (Ball ball : balls) {
            if (ball.isVisible()) {
                ball.move();
            }
        }

        for (Ball ball : balls) {
            if (ball.isVisible()) {
                ball.checkCollisions(balls);
            }
        }

        repaint();

        boolean hasMovingBalls = false;
        for (Ball ball : balls) {
            if (ball.isVisible() && ball.isMoving()) {
                hasMovingBalls = true;
            }
        }

        if (!hasMovingBalls) {
            simulationTimer.stop();
        }
    }

    public void mousePressed(MouseEvent event) {
        Coord mousePosition = new Coord(event);

        for (Ball ball : balls) {
            if (ball.isVisible()) {
                ball.setAimPosition(mousePosition);
            }
        }

        repaint();                          //  To show aiming line
    }

    public void mouseReleased(MouseEvent e) {
        for (Ball ball : balls) {
            if (ball.isVisible()) {
                ball.shoot();
            }
        }

        if (!simulationTimer.isRunning()) {
            simulationTimer.start();
        }
    }

    public void mouseDragged(MouseEvent event) {
        Coord mousePosition = new Coord(event);

        for (Ball ball : balls) {
            if (ball.isVisible()) {
                ball.updateAimPosition(mousePosition);
            }
        }

        repaint();
    }

    // Obligatory empty listener methods
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}

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

        for (Ball ball : balls) {
            if (ball.isVisible()) {
                ball.paint(g2D);
            }
        }
    }
}  // end class Table
