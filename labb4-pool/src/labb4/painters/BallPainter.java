package labb4.painters;

import labb4.Ball;
import labb4.Config;
import labb4.CueBall;
import labb4.Vector2D;

import java.awt.*;

public class BallPainter {
    public void draw(Graphics2D g, Ball ball) {
        Rectangle bounds = ball.getBounds();

        g.setColor(Color.BLACK);
        g.fillOval(
                bounds.x,
                bounds.y,
                bounds.width,
                bounds.height
        );

        g.setColor(ball.color);
        g.fillOval(
                bounds.x + Config.BALL_BORDER,
                bounds.y + Config.BALL_BORDER,
                bounds.width - 2 * Config.BALL_BORDER,
                bounds.height - 2 * Config.BALL_BORDER
        );
    }

    public void drawAimPosition(Graphics2D g, CueBall ball) {
        Vector2D position = ball.getAimPosition();

        if (position != null) {
            Vector2D initialPosition = ball.getInitialAimPosition();

            g.setColor(Config.AIM_COLOR);
            g.setStroke(new BasicStroke(2));
            g.drawLine((int) initialPosition.getX(), (int) initialPosition.getY(), (int) position.getX(), (int) position.getY());
        }
    }
}
