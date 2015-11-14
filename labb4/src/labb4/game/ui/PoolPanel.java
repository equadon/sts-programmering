package labb4.game.ui;

import labb4.game.*;
import labb4.game.interfaces.TableListener;
import labb4.game.objects.CueBall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Logger;

public class PoolPanel extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener, TableListener {
    private static final Logger LOG = Logger.getLogger(PoolPanel.class.getName());

    private final Timer timer;
    private final JFrame frame;
    private final Player player1;
    private final Player player2;
    private final JLabel turnLabel;

    private Table table;
    private GameType gameType;

    public PoolPanel(JFrame frame, Player player1, Player player2, JLabel turnLabel) {
        this.frame = frame;
        this.player1 = player1;
        this.player2 = player2;
        this.turnLabel = turnLabel;

        timer = new Timer((int) (1000.0 / Config.FRAMES_PER_SECOND), this);

        setFocusable(true);

        gameType = GameType.EightBall;
        newGame();

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    private void newGame() {
        player1.reset();
        player2.reset();

        table = TableFactory.createPoolTable(gameType, player1, player2);
        table.addListener(this);
        table.addListener(player1);
        table.addListener(player2);
        table.init();

        setPreferredSize(new Dimension(table.width, table.height));
        setSize(new Dimension(table.width, table.height));
        frame.pack();

        repaint();
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
        if (timer.isRunning()) {
            LOG.warning("Timer is running, can't click.");
        } else if (SwingUtilities.isLeftMouseButton(e)) {
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

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_1) {
            gameType = GameType.values()[0];
            newGame();
        } else if (e.getKeyCode() == KeyEvent.VK_2) {
            gameType = GameType.values()[1];
            newGame();
        } else if (e.getKeyCode() == KeyEvent.VK_3) {
            gameType = GameType.values()[2];
            newGame();
        }
    }

    @Override
    public void turnChanged(Player currentPlayer) {
        turnLabel.setText("Turn: " + currentPlayer.name);
    }

    @Override
    public void pointsAdded(Player player, int points) {}

    @Override
    public void gameCreated() {}
}
