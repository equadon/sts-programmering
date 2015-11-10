package labb4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
class Table extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    private final int   WIDTH          = 300;
    private final int   HEIGHT         = 500;
    private final int   WALL_THICKNESS = 20;
    private final Color COLOR          = Color.green;
    private final Color WALL_COLOR     = Color.black;
    private       Ball  ball1, ball2;
    private final Timer simulationTimer;

    public final Rectangle innerBounds;
    public final Ball[] balls;

    Table() {

        setPreferredSize(new Dimension(WIDTH + 2 * WALL_THICKNESS,
                HEIGHT + 2 * WALL_THICKNESS));
        createInitialBalls();

        addMouseListener(this);
        addMouseMotionListener(this);

        simulationTimer = new Timer((int) (1000.0 / Pool.UPDATE_FREQUENCY), this);

        innerBounds = new Rectangle(
                getBounds().x + WALL_THICKNESS,
                getBounds().y + WALL_THICKNESS,
                WIDTH,
                HEIGHT
        );

        balls = new Ball[] {ball1, ball2};
    }

    private void createInitialBalls(){
        final Coord firstInitialPosition = new Coord(100, 100);
        final Coord secondInitialPosition = new Coord(200, 200);
        ball1 = new Ball(this, firstInitialPosition);
        ball2 = new Ball(this, secondInitialPosition);
    }

    public void actionPerformed(ActionEvent e) {          // Timer event
        ball1.move();
        ball2.move();
        repaint();
        if (!ball1.isMoving() && !ball2.isMoving()) {
            simulationTimer.stop();
        }
    }

    public void mousePressed(MouseEvent event) {
        Coord mousePosition = new Coord(event);
        ball1.setAimPosition(mousePosition);
        ball2.setAimPosition(mousePosition);
        repaint();                          //  To show aiming line
    }

    public void mouseReleased(MouseEvent e) {
        ball1.shoot();
        ball2.shoot();
        if (!simulationTimer.isRunning()) {
            simulationTimer.start();
        }
    }

    public void mouseDragged(MouseEvent event) {
        Coord mousePosition = new Coord(event);
        ball1.updateAimPosition(mousePosition);
        ball2.updateAimPosition(mousePosition);
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

        ball1.paint(g2D);
        ball2.paint(g2D);
    }
}  // end class Table
