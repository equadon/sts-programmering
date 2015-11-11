package labb4.painters;

import labb4.Ball;

import java.awt.*;

public class BallPainter {
    public void draw(Graphics2D g, Ball ball) {
        Rectangle bounds = ball.getBounds();

        g.setColor(ball.color);
        g.fillOval(bounds.x, bounds.y, bounds.width, bounds.height);
    }
}
