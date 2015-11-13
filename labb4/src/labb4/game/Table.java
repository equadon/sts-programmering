package labb4.game;

import labb4.game.initializers.BallInitializer;
import labb4.game.painters.TablePainter;

import java.awt.*;

public class Table {
    private final TablePainter painter;
    private final int width;
    private final int height;

    private final Ball[] balls;

    public Table(int width, int height, BallInitializer ballInitializer) {
        this.width = width;
        this.height = height;

        painter = new TablePainter();
        balls = ballInitializer.init(this);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Ball[] getBalls() {
        return balls;
    }

    public void draw(Graphics2D g) {
        painter.draw(g, this);
    }
}
