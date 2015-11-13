package labb4.game;

import labb4.game.initializers.BallInitializer;
import labb4.game.objects.Ball;
import labb4.game.objects.CueBall;
import labb4.game.objects.Hole;
import labb4.game.painters.TablePainter;

import java.awt.*;

public class Table {
    private final TablePainter painter;

    private final Rectangle bounds;
    private final Rectangle playableBounds;

    private final CueBall cueBall;
    private final Ball[] balls;
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

        cueBall = ballInitializer.createCueBall(this);
        balls = ballInitializer.createBalls(this);

        holes = new Hole[6];

        bounds = new Rectangle(0, 0, width, height);
        playableBounds = new Rectangle(
                outerBorderSize + innerBorderSize,
                outerBorderSize + innerBorderSize,
                width - 2 * (outerBorderSize + innerBorderSize),
                height - 2 * (outerBorderSize + innerBorderSize)
        );

        createHoles();
    }

    private void createHoles() {
        Vector2D position;

        for (int i = 0; i < holes.length; i++) {
            int row = i / 2;
            int col = i % 2;

            position = new Vector2D(playableBounds.x + col * playableBounds.width, playableBounds.y + row * playableBounds.height);

            holes[i] = new Hole(this, position, Config.DEFAULT_HOLE_RADIUS);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Rectangle getPlayableBounds() {
        return playableBounds;
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
}
