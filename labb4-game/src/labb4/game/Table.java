package labb4.game;

import labb4.game.handlers.GameHandler;
import labb4.game.initializers.BallInitializer;
import labb4.game.objects.CueBall;
import labb4.game.objects.Pocket;
import labb4.game.objects.PoolBall;
import labb4.game.ui.painters.TablePainter;

import java.awt.*;
import java.util.List;

public class Table {
    private final TablePainter painter;

    private final Rectangle bounds;
    private final Rectangle playableBounds;

    private final List<PoolBall> balls;
    private final Pocket[] pockets;

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

    public Table(int playfieldWidth, int playfieldHeight, int outerBorderSize, int innerBorderSize,
                 BallInitializer ballInitializer, GameHandler gameHandler, Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.width = playfieldWidth + 2 * (outerBorderSize + innerBorderSize);
        this.height = playfieldHeight + 2 * (outerBorderSize + innerBorderSize);
        this.outerBorderSize = outerBorderSize;
        this.innerBorderSize = innerBorderSize;

        this.gameHandler = gameHandler;
        gameHandler.setTable(this);

        painter = new TablePainter();

        pockets = new Pocket[6];

        bounds = new Rectangle(0, 0, width, height);
        playableBounds = new Rectangle(
                outerBorderSize + innerBorderSize,
                outerBorderSize + innerBorderSize,
                width - 2 * (outerBorderSize + innerBorderSize),
                height - 2 * (outerBorderSize + innerBorderSize)
        );

        createPockets();

        balls = ballInitializer.createBalls(this);

        changeTurn();
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
    public void pocket(PoolBall ball) {
        balls.remove(ball);
    }

    public void add(PoolBall ball) {
        if (!balls.contains(ball)) {
            balls.add(ball);
        }
    }

    private void createPockets() {
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
}
