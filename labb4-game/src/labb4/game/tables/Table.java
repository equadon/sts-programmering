package labb4.game.tables;

import labb4.game.Config;
import labb4.game.GameType;
import labb4.game.Player;
import labb4.game.Vector2D;
import labb4.game.handlers.GameHandler;
import labb4.game.initializers.BallInitializer;
import labb4.game.objects.CueBall;
import labb4.game.objects.Pocket;
import labb4.game.objects.PoolBall;
import labb4.game.ui.painters.TablePainter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private final TablePainter painter;

    private final Rectangle bounds;
    private final Rectangle playableBounds;

    protected final List<PoolBall> balls;
    protected final Pocket[] pockets;

    public final int width;
    public final int height;
    public final int outerBorderSize;
    public final int innerBorderSize;

    private final GameHandler gameHandler;
    private final Player player1;
    private final Player player2;

    private Player currentPlayer;

    private String turnText;
    private String message;

    public Table(int width, int height, GameHandler gameHandler, Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        outerBorderSize = Config.TABLE_OUTER_BORDER_SIZE;
        innerBorderSize = Config.TABLE_INNER_BORDER_SIZE;

        this.width = width + 2 * (outerBorderSize + innerBorderSize);
        this.height = height + 2 * (outerBorderSize + innerBorderSize);

        this.gameHandler = gameHandler;
        gameHandler.setTable(this);

        painter = new TablePainter();

        pockets = new Pocket[6];
        balls = new ArrayList<>();

        bounds = new Rectangle(0, 0, this.width, this.height);
        playableBounds = new Rectangle(
                outerBorderSize + innerBorderSize,
                outerBorderSize + innerBorderSize,
                this.width - 2 * (outerBorderSize + innerBorderSize),
                this.height - 2 * (outerBorderSize + innerBorderSize)
        );

        createPockets();
        currentPlayer = player1;

        createBalls();
    }

    public boolean isUpdating() {
        for (PoolBall ball : balls) {
            if (ball.isMoving()) {
                return true;
            }
        }

        return false;
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

    public GameHandler getHandler() {
        return gameHandler;
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

    protected void createBalls() {}

    protected void createPockets() {
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
    }

    public void gameOver() {
        /*balls.clear();

        for (Pocket remove : pockets)
            remove.empty();*/
    }

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
