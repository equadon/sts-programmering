package labb4.game;

import labb4.game.handlers.GameHandler;
import labb4.game.initializers.BallInitializer;
import labb4.game.objects.CueBall;
import labb4.game.objects.Hole;
import labb4.game.objects.PoolBall;
import labb4.game.ui.painters.TablePainter;

import java.awt.*;

public class Table {
    private final TablePainter painter;

    private final Rectangle bounds;
    private final Rectangle playableBounds;

    private final CueBall cueBall;
    private final PoolBall[] balls;
    private final Hole[] holes;

    public final int width;
    public final int height;
    public final int outerBorderSize;
    public final int innerBorderSize;

    private final GameHandler gameHandler;
    private final Player player1;
    private final Player player2;

    private Player currentPlayer;

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

        holes = new Hole[6];

        bounds = new Rectangle(0, 0, width, height);
        playableBounds = new Rectangle(
                outerBorderSize + innerBorderSize,
                outerBorderSize + innerBorderSize,
                width - 2 * (outerBorderSize + innerBorderSize),
                height - 2 * (outerBorderSize + innerBorderSize)
        );

        createHoles();

        cueBall = ballInitializer.createCueBall(this);
        balls = ballInitializer.createBalls(this);

        changeTurn();
    }

    public boolean isUpdating() {
        if (cueBall.isMoving()) return true;

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

    public CueBall getCueBall() {
        return cueBall;
    }

    public PoolBall[] getBalls() {
        return balls;
    }

    public Hole[] getHoles() {
        return holes;
    }

    public GameHandler getHandler() {
        return gameHandler;
    }

    public void draw(Graphics2D g) {
        painter.draw(g, this);

        for (Hole hole : holes) {
            hole.draw(g);
        }

        if (cueBall.isVisible()) {
            cueBall.draw(g);
        }

        for (PoolBall ball : balls) {
            if (ball.isVisible()) {
                ball.draw(g);
            }
        }
    }

    public void update() {
        cueBall.update();

        for (PoolBall ball : balls) {
            if (ball.isVisible()) {
                ball.update();
            }
        }

        if (cueBall.isVisible()) {
            cueBall.handleCollisions();
        }

        for (PoolBall ball : balls) {
            if (ball.isVisible()) {
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

    private void createHoles() {
        Vector2D position;

        for (int i = 0; i < holes.length; i++) {
            int row = i / 2;
            int col = i % 2;

            int xOffset = 0;
            int yOffset = 0;
            if (col == 0 && (row == 0 || row == 2)) {
                xOffset = (int) (Config.DEFAULT_HOLE_RADIUS * 0.5);
                yOffset = (int) (Config.DEFAULT_HOLE_RADIUS * 0.5);

                if (row == 2) {
                    yOffset *= -1;
                }
            } else if (col == 1&& (row == 0 || row == 2)) {
                xOffset = -(int) (Config.DEFAULT_HOLE_RADIUS * 0.5);
                yOffset = (int) (Config.DEFAULT_HOLE_RADIUS * 0.5);

                if (row == 2) {
                    yOffset *= -1;
                }
            }

            position = new Vector2D(xOffset + playableBounds.x + col * playableBounds.width, yOffset + playableBounds.y + row * (playableBounds.height / 2.0));

            holes[i] = new Hole(this, position, Config.DEFAULT_HOLE_RADIUS);
        }
    }
}
