package labb4.game;

import labb4.game.initializers.BallInitializer;
import labb4.game.objects.Ball;
import labb4.game.objects.CueBall;
import labb4.game.objects.Hole;
import labb4.game.objects.PoolBall;
import labb4.game.painters.TablePainter;

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

    public Table(int playfieldWidth, int playfieldHeight, int outerBorderSize, int innerBorderSize, BallInitializer ballInitializer) {
        this.width = playfieldWidth + 2 * (outerBorderSize + innerBorderSize);
        this.height = playfieldHeight + 2 * (outerBorderSize + innerBorderSize);
        this.outerBorderSize = outerBorderSize;
        this.innerBorderSize = innerBorderSize;

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
    }

    private void createHoles() {
        Vector2D position;

        for (int i = 0; i < holes.length; i++) {
            int row = i / 2;
            int col = i % 2;

            position = new Vector2D(playableBounds.x + col * playableBounds.width, playableBounds.y + row * (playableBounds.height / 2.0));

            holes[i] = new Hole(this, position, Config.DEFAULT_HOLE_RADIUS);
        }
    }

    public boolean isUpdating() {
        if (cueBall.isMoving()) return true;

        for (Ball ball : balls) {
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

    public Ball[] getBalls() {
        return balls;
    }

    public void draw(Graphics2D g) {
        painter.draw(g, this);

        for (Hole hole : holes) {
            hole.draw(g);
        }

        cueBall.draw(g);

        for (Ball ball : balls) {
            ball.draw(g);
        }
    }

    public void update() {
        cueBall.update();

        for (Ball ball : balls) {
            ball.update();
        }

        cueBall.handleCollisions();
        for (Ball ball : balls) {
            ball.handleCollisions();
        }
    }
}
