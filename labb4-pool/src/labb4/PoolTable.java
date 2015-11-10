package labb4;

import labb4.initializers.BallsInitializer;
import labb4.painters.TablePainter;

import java.awt.*;

public class PoolTable {
    public final int width;
    public final int height;
    public final int borderSize;
    public final int innerBorderSize;

    private final BallsInitializer ballsInitializer;
    private final TablePainter painter;
    private final Ball[] balls;
    private final Hole[] holes;

    public PoolTable(int width, int height, BallsInitializer ballsInitializer, TablePainter painter) {
        this(width, height, Config.BORDER_SIZE, Config.INNER_BORDER_SIZE, ballsInitializer, painter);
    }

    /**
     * Pool table constructor.
     * @param width width of table in cm
     * @param height height of table in cm
     * @param ballsInitializer initializer to be used when setting up the balls
     */
    public PoolTable(int width, int height, int borderSize, int innerBorderSize,
                     BallsInitializer ballsInitializer, TablePainter painter) {
        this.width = width;
        this.height = height;
        this.borderSize = borderSize;
        this.innerBorderSize = innerBorderSize;
        this.ballsInitializer = ballsInitializer;
        this.painter = painter;

        balls = ballsInitializer.init();

        holes = new Hole[6];
        for (int i = 0; i < holes.length; i++) {
            int row = i / 2;
            int col = i % 2;

            holes[i] = new Hole(row, col, Config.HOLE_RADIUS);
        }
    }

    public Hole[] getHoles() {
        return holes;
    }

    public Rectangle getBounds() {
        return new Rectangle(0, 0, width, height);
    }

    public Rectangle getInnerBounds() {
        return new Rectangle(
                borderSize,
                borderSize,
                width - 2 * borderSize,
                height - 2 * borderSize
        );
    }

    public Rectangle getPlayableBounds() {
        int border = borderSize + innerBorderSize;

        return new Rectangle(
                border,
                border,
                width - 2 * border,
                height - 2 * border
        );
    }

    public void draw(Graphics2D g, TablePanel panel) {
        painter.draw(g, panel);
    }

    public void update() {
        // Update ball positions
    }
}
