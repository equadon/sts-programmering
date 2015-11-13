package labb4.game.ui;

import labb4.game.*;
import labb4.game.objects.CueBall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PoolPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
    private final Timer timer;

    private Table table;
    private GameType gameType;

    public PoolPanel() {
        timer = new Timer((int) (1000.0 / Config.FRAMES_PER_SECOND), this);

        gameType = GameType.EightBall;
        table = TableFactory.createStandardPoolTable(gameType);

        setPreferredSize(new Dimension(table.width, table.height));

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        table.draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (table.isUpdating()) {
            table.update();

            repaint();
        } else {
            timer.stop();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            Vector2D position = Vector2D.fromMouseEvent(e);

            CueBall cueBall = table.getCueBall();

            if (cueBall.getBounds().contains(position.x, position.y)) {
                if (cueBall.getPosition().distanceTo(position) < cueBall.getRadius()) {
                    cueBall.setAim(position);

                    repaint();
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        CueBall cueBall = table.getCueBall();

        if (SwingUtilities.isLeftMouseButton(e) && cueBall.isAiming()) {
            cueBall.shoot();

            repaint();

            timer.start();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        Vector2D position = Vector2D.fromMouseEvent(e);

        if (SwingUtilities.isLeftMouseButton(e) && table.getCueBall().isAiming()) {
            table.getCueBall().updateAim(position);

            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}
}
