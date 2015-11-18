package labb4.game.ui;

import labb4.game.*;
import labb4.game.interfaces.Aimable;
import labb4.game.interfaces.Placeable;
import labb4.game.objects.Ball;
import labb4.game.objects.PoolBall;
import labb4.game.tables.Table;
import labb4.game.ui.contextmenus.ContextMenuListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PoolPanel extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {
    private static final Logger LOG = Logger.getLogger(PoolPanel.class.getName());

    private final Timer timer;
    private final JFrame frame;

    private final Player[] players;

    private Table table;
    private GameType gameType;

    private ContextMenuListener popUpListener;
    private PoolGameObserver gameObserver;

    private List<Placeable> placing; // list of objects being placed

    public PoolPanel(JFrame frame, Player[] players) {
        this.frame = frame;
        this.players = players;

        timer = new Timer((int) (1000.0 / Config.FRAMES_PER_SECOND), this);

        setFocusable(true);

        newGame(GameType.Snooker);

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void newGame(GameType type) {
        this.gameType = type;

        frame.setTitle("STS Pool Project: " + Utility.splitCamelCase(gameType.name()));

        for (Player player : players) {
            player.reset();
        }

        placing = new ArrayList<>();

        table = Table.createTable(gameType, players);

        // Update window size
        setPreferredSize(new Dimension(table.width, table.height));
        setSize(new Dimension(table.width, table.height));
        frame.pack();

        // Listeners
        if (popUpListener != null) {
            removeMouseListener(popUpListener);
        }

        popUpListener = new ContextMenuListener(this, table);
        addMouseListener(popUpListener);

        gameObserver = new PoolGameObserver(this);
        table.addObserver(gameObserver);

        table.newGame();

        repaint();
    }

    public Table getTable() {
        return table;
    }

    public Placeable getPlacing() {
        return placing.get(0);
    }

    public void startPlacing(Placeable placeable) {
        placing.add(placeable);

        getPlacing().startPlacing();
    }

    public boolean place(Vector2D position) {
        if (isPlacing()) {
            Placeable placeable = placing.get(0);

            if (placeable.place(position)) {
                placing.remove(placeable);

                repaint();

                // If more balls to place, restart placing process
                if (placing.size() > 0) {
                    startPlacing(placing.get(0));
                }

                return true;
            }
        }

        return false;
    }

    public boolean isPlacing() {
        return placing.size() > 0;
    }

    private PoolBall getBall(Vector2D position) {
        for (PoolBall ball : table.getBalls()) {
            if (ball.contains(position)) {
                return ball;
            }
        }

        return null;
    }

    private Aimable getAimable(Vector2D position) {
        for (PoolBall ball : table.getBalls()) {
            if (ball instanceof Aimable) {
                if (ball.contains(position)) {
                    return (Aimable) ball;
                }
            }
        }

        return null;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        table.draw(g);

        if (isPlacing()) {
            ((Ball) getPlacing()).draw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (table.isUpdating()) {
            table.update();

            repaint();
        } else {
            table.endTurn();
            timer.stop();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (timer.isRunning()) return;

        Vector2D position = Vector2D.fromMouseEvent(e);

        if (SwingUtilities.isLeftMouseButton(e)) {
            if (!isPlacing()) {
                Aimable aimable = getAimable(position);
                if (aimable != null) {
                    aimable.setAim(position);
                    repaint();
                }
            }
        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            // Start placing ball
            PoolBall ball = getBall(position);
            if (ball != null) {
                startPlacing(ball);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Vector2D position = Vector2D.fromMouseEvent(e);

        if (SwingUtilities.isLeftMouseButton(e)) {
            if (isPlacing()) {
                if (!place(position)) {
                    LOG.warning("Invalid placement position: " + position);
                }
            } else {
                for (Aimable aimable : table.getAimable()) {
                    if (aimable.isAiming()) {
                        aimable.shoot();

                        repaint();

                        timer.start();
                        table.getHandler().beginTurn(table.getCurrentPlayer());

                        break;
                    }
                }
            }
        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            if (isPlacing()) {
                if (!place(position)) {
                    LOG.warning("Invalid placement position: " + position);
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        Vector2D position = Vector2D.fromMouseEvent(e);

        if (SwingUtilities.isLeftMouseButton(e)) {
            for (PoolBall ball : table.getBalls()) {
                if (ball instanceof Aimable) {
                    ((Aimable) ball).updateAim(position);
                }
            }

            repaint();
        } else if (SwingUtilities.isMiddleMouseButton(e) && isPlacing()) {
            getPlacing().updatePlacement(position);

            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (isPlacing()) {
            Vector2D position = Vector2D.fromMouseEvent(e);
            getPlacing().updatePlacement(position);

            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
