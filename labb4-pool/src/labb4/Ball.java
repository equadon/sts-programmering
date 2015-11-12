package labb4;

import labb4.painters.BallPainter;

import java.awt.*;

public class Ball {
    private int x;
    private int y;
    private final BallPainter painter;

    public final int radius;
    public final Color color;

    private boolean visible;

    public Ball(int x, int y, int radius, Color color, BallPainter painter) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        this.painter = painter;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 2 * radius, 2 * radius);
    }

    public boolean isVisible() {
        return visible;
    }

    public void remove() {
        visible = false;
    }

    public void draw(Graphics2D g) {
        painter.draw(g, this);
    }
}
