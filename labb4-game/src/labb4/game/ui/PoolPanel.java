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
    private final Player player1;
    private final Player player2;
    private final JLabel turnLabel;

    private Table table;
    private GameType gameType;

    private ContextMenuListener popUpListener;
    private PoolGameObserver gameObserver;

    private List<Placeable> placing; // list of objects being placed

    public PoolPanel(JFrame frame, Player player1, Player player2, JLabel turnLabel) {
        this.frame = frame;
        this.player1 = player1;
        this.player2 = player2;
        this.turnLabel = turnLabel;

        timer = new Timer((int) (1000.0 / Config.FRAMES_PER_SECOND), this);

        setFocusable(true);

        gameType = GameType.NineBall;
        newGame(gameType);

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void newGame(GameType type) {
        this.gameType = type;

        player1.reset();
        player2.reset();

        placing = new ArrayList<>();

        table = Table.createTable(gameType, player1, player2);

        setPreferredSize(new Dimension(table.width, table.height));
        setSize(new Dimension(table.width, table.height));
        frame.pack();

        if (popUpListener != null) {
            removeMouseListener(popUpListener);
        }

        popUpListener = new ContextMenuListener(this, table);
        addMouseListener(popUpListener);

        gameObserver = new PoolGameObserver(this);
        table.addObserver(gameObserver);

        repaint();
    }

    public Table getTable() {
        return table;
    }

    public void setTurn(String text) {
        turnLabel.setText(text);
    }

    public void setMessages(String above, String below) {
        table.setMessage(above);
        table.setTurnText(below);
    }

    public Placeable nextPlacement() {
        return placing.get(0);
    }

    public void startPlacing(Placeable placeable) {
        placing.add(placeable);

        nextPlacement().startPlacing();
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

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        table.draw(g);

        if (isPlacing()) {
            ((Ball) nextPlacement()).draw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (table.isUpdating()) {
            table.update();

            repaint();
        } else {
            table.getHandler().endTurn();
            timer.stop();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && timer.isRunning()) {
            LOG.warning("Timer is running, can't click.");
        } else if (SwingUtilities.isLeftMouseButton(e)) {
            Vector2D position = Vector2D.fromMouseEvent(e);

            if (!isPlacing()) {
                for (PoolBall ball : table.getBalls()) {
                    if (ball instanceof Aimable) {
                        if (ball.getBounds().contains(position.x, position.y) && ball.getPosition().distanceTo(position) < ball.getRadius()) {
                            ((Aimable) ball).setAim(position);
                            repaint();

                            break;
                        }
                    }
                }
            }
        }

        if (!isPlacing() && SwingUtilities.isMiddleMouseButton(e)) {
            Vector2D position = Vector2D.fromMouseEvent(e);

            for (PoolBall ball : table.getBalls()) {
                if (ball.contains(position)) {
                    startPlacing(ball);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (isPlacing()) {
                Vector2D position = Vector2D.fromMouseEvent(e);
                if (!place(position)) {
                    LOG.warning("Invalid placement position: " + position);
                }
            } else {
                for (PoolBall ball : table.getBalls()) {
                    if (ball instanceof Aimable) {
                        Aimable aimable = (Aimable) ball;
                        if (aimable.isAiming()) {
                            aimable.shoot();

                            repaint();

                            timer.start();

                            break;
                        }
                    }
                }
            }
        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            if (isPlacing()) {
                Vector2D position = Vector2D.fromMouseEvent(e);
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
        }

        if (SwingUtilities.isMiddleMouseButton(e) && isPlacing()) {
            nextPlacement().updatePlacement(position);

            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (isPlacing()) {
            Vector2D position = Vector2D.fromMouseEvent(e);
            nextPlacement().updatePlacement(position);

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
