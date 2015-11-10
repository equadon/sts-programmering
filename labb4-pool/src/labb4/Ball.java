package labb4;

import labb4.painters.BallPainter;

import java.awt.*;

public class Ball {
    public final double radius;
    private final BallPainter painter;

    private boolean visible;

    public Ball(double radius, BallPainter painter) {
        this.radius = radius;
        this.painter = painter;
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
