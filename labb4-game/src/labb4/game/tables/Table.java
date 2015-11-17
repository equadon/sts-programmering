package labb4.game.tables;

import labb4.game.Config;
import labb4.game.GameType;
import labb4.game.Player;
import labb4.game.Vector2D;
import labb4.game.handlers.GameHandler;
import labb4.game.interfaces.GameObserver;
import labb4.game.objects.Pocket;
import labb4.game.objects.PoolBall;
import labb4.game.ui.painters.TablePainter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Table {
    private final TablePainter painter;

    private final Rectangle bounds;
    private final Rectangle playableBounds;

    protected List<PoolBall> balls;
    protected Pocket[] pockets;

    protected final List<GameObserver> listeners;

    public final int width;
    public final int height;
    public final int outerBorderSize;
    public final int innerBorderSize;

    protected GameHandler handler;

    private final Player player1;
    private final Player player2;

    private Player currentPlayer;

    private String turnText;
    private String message;

    public Table(int width, int height, GameHandler handler, Player player1, Player player2) {
        this.handler = handler;
        this.player1 = player1;
        this.player2 = player2;

        outerBorderSize = Config.TABLE_OUTER_BORDER_SIZE;
        innerBorderSize = Config.TABLE_INNER_BORDER_SIZE;

        this.width = width + 2 * (outerBorderSize + innerBorderSize);
        this.height = height + 2 * (outerBorderSize + innerBorderSize);

        painter = new TablePainter();

        bounds = new Rectangle(0, 0, this.width, this.height);
        playableBounds = new Rectangle(
                outerBorderSize + innerBorderSize,
                outerBorderSize + innerBorderSize,
                this.width - 2 * (outerBorderSize + innerBorderSize),
                this.height - 2 * (outerBorderSize + innerBorderSize)
        );

        listeners = new ArrayList<>();

        currentPlayer = player1;

        pockets = createPockets();
        balls = createBalls();
    }

    public boolean isUpdating() {
        for (PoolBall ball : balls) {
            if (ball.isMoving()) {
                return true;
            }
        }

        return false;
    }

    public GameHandler getHandler() {
        return handler;
    }

    public Rectangle getBounds() {
        return bounds;
    }
    public Rectangle getPlayableBounds() {
        return playableBounds;
    }

    public PoolBall[] getBalls() {
        return balls.toArray(new PoolBall[balls.size()]);
    }
    public Pocket[] getPockets() {
        return pockets;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getTurnText() {
        return turnText;
    }
    public void setTurnText(String message) {
        this.turnText = message;
    }

    public void draw(Graphics2D g) {
        painter.draw(g, this);

        for (Pocket pocket : pockets) {
            pocket.draw(g);
        }

        for (PoolBall ball : balls) {
            if (ball.isVisible()) {
                ball.draw(g);
            }
        }
    }

    public void update() {
        for (PoolBall ball : balls) {
            if (ball.isVisible()) {
                ball.update();
            }
        }

        for (PoolBall ball : getBalls()) {
            if (!ball.isPlacing() && ball.isVisible()) {
                ball.handleCollisions();
            }
        }
    }

    public void changeTurn() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    /**
     * Ball pocketed, remove from list.
     */
    public void remove(PoolBall ball) {
        balls.remove(ball);
    }
    public void add(PoolBall ball) {
        if (!balls.contains(ball)) {
            balls.add(ball);
        }
    }

    protected List<PoolBall> createBalls() {
        return new ArrayList<>();
    };

    protected Pocket[] createPockets() {
        pockets = new Pocket[Config.POCKET_COUNT];
        Vector2D position;

        for (int i = 0; i < pockets.length; i++) {
            int row = i / 2;
            int col = i % 2;

            int xOffset = 0;
            int yOffset = 0;
            if (col == 0 && (row == 0 || row == 2)) {
                xOffset = (int) (Config.DEFAULT_POCKET_RADIUS * 0.5);
                yOffset = (int) (Config.DEFAULT_POCKET_RADIUS * 0.5);

                if (row == 2) {
                    yOffset *= -1;
                }
            } else if (col == 1&& (row == 0 || row == 2)) {
                xOffset = -(int) (Config.DEFAULT_POCKET_RADIUS * 0.5);
                yOffset = (int) (Config.DEFAULT_POCKET_RADIUS * 0.5);

                if (row == 2) {
                    yOffset *= -1;
                }
            }

            position = new Vector2D(xOffset + playableBounds.x + col * playableBounds.width, yOffset + playableBounds.y + row * (playableBounds.height / 2.0));

            pockets[i] = new Pocket(this, position, Config.DEFAULT_POCKET_RADIUS);
        }

        return pockets;
    }

    public void addObserver(GameObserver observer) {
        handler.addObserver(observer);
    }
    public void removeObserver(GameObserver observer) {
        handler.removeObserver(observer);
    }

    /**
     * Helper method to create a Table object based on chosen game type.
     */
    public static Table createTable(GameType type, Player player1, Player player2) {
        switch (type) {
            case EightBall:
                return new EightBallTable(player1, player2);

            case NineBall:
                return new NineBallTable(player1, player2);

            case Snooker:
                return new SnookerTable(player1, player2);
        }

        return null;
    }
}
