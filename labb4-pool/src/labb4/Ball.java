package labb4;

import labb4.painters.BallPainter;

import java.awt.*;

public class Ball {
    protected final BallPainter painter;

    public final int radius;
    public final Color color;

    private Vector2D position;
    private Vector2D velocity;

    private boolean visible;

    public Ball(int x, int y, int radius, Color color, BallPainter painter) {
        position = new Vector2D(x, y);
        velocity = new Vector2D(0, 0);

        this.radius = radius;
        this.color = color;
        this.painter = painter;
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) position.getX() - radius, (int) position.getY() - radius, 2 * radius, 2 * radius);
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
