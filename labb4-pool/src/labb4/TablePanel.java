package labb4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TablePanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
    private final Timer timer;

    private PoolTable table;

    private CueBall aimingBall;

    public TablePanel() {
        timer = new Timer((int) (1000.0 / Config.FPS), this);

        setupTable();

        setPreferredSize(new Dimension(
                table.width,
                table.height
        ));

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public PoolTable getTable() {
        return table;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        table.draw(g, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        table.update();
    }

    private void setupTable() {
        table = PoolTableFactory.createTable(GameType.EIGHT_BALL);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Vector2D position = new Vector2D(e.getX(), e.getY());

        for (Ball ball : table.getBalls()) {
            // Only allow aiming cue balls
            if (ball instanceof CueBall) {
                if (position.distanceTo(ball.getPosition()) <= ball.radius) {
                    aimingBall = (CueBall) ball;
                    aimingBall.setAimPosition(position);

                    repaint();
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (aimingBall != null) {
            aimingBall.shoot();
            aimingBall = null;

            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Vector2D position = new Vector2D(e.getX(), e.getY());

        if (aimingBall != null) {
            aimingBall.updateAimPosition(position);

            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
}
