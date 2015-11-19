package labb4.game.ui.painters;

import labb4.game.Config;
import labb4.game.Utility;
import labb4.game.Vector2D;
import labb4.game.interfaces.Aimable;
import labb4.game.objects.Ball;
import labb4.game.objects.GameObject;
import labb4.game.objects.Pocket;
import labb4.game.objects.PoolBall;

import java.awt.*;

public class BallPainter extends ObjectPainter {
    private static final int FONT_SIZE = (int) (6 * Config.RESIZE_FACTOR - 3);
    private static final Font NUMBER_FONT = new Font("Arial", Font.BOLD, FONT_SIZE);
    private static final double ANGLE = Math.toRadians(30);

    @Override
    public void draw(Graphics2D g, GameObject object) {
        Ball ball = (Ball) object;
        Rectangle.Double bounds = ball.getBounds();

        int x = (int) Math.round(bounds.x);
        int y = (int) Math.round(bounds.y);
        int width = (int) Math.round(bounds.width);
        int height = (int) Math.round(bounds.height);

        drawBall(g, ball.getColor(), x, y, width, height);

        if (ball instanceof PoolBall) {
            PoolBall poolBall = (PoolBall) ball;

            if (poolBall.striped) {
                drawStripes(g, poolBall);
            }

            if (poolBall.showNumber) {
                drawNumber(g, poolBall);
            }
        }

        // Reflection
        if (!(ball instanceof Pocket)) {
            int reflectionRadius = (int) (ball.getRadius() / 3.5);

            g.setColor(Config.BALL_WHITE_COLOR);
            g.fillOval(x + reflectionRadius, y + reflectionRadius, 2*reflectionRadius, 2*reflectionRadius);
        }


        // Draw aim line
        if (object instanceof Aimable) {
            drawAim(g, ball);
        }

        // Border
        g.setColor(Config.BALL_BORDER_COLOR);
        g.setStroke(new BasicStroke(Config.BALL_BORDER_SIZE));
        g.drawOval(
                (int) Math.round(bounds.x + Config.BALL_BORDER_SIZE / 2.0),
                (int) Math.round(bounds.y + Config.BALL_BORDER_SIZE / 2.0),
                width - 2 * Config.BALL_BORDER_SIZE,
                height - 2 * Config.BALL_BORDER_SIZE);

        if (Config.DISPLAY_VELOCITY_VECTORS && ball.isMoving()) {
            drawVelocityVector(g, ball);
        }
    }

    private void drawVelocityVector(Graphics2D g, Ball ball) {
        Point start = new Point((int) Math.round(ball.getPosition().x), (int) Math.round(ball.getPosition().y));

        Vector2D velocity = ball.getVelocity().clone();
        double magnitude = velocity.length();
        velocity.normalize().multiply(20 * magnitude);

        Point end = new Point(start.x + (int) Math.round(velocity.x), start.y + (int) Math.round(velocity.y));

        g.setColor(Utility.invertColor(ball.getColor()));
        g.drawLine(start.x, start.y, end.x, end.y);
    }

    private void drawBall(Graphics2D g, Color color, int x, int y, int width, int height) {
        if (Config.DISPLAY_BOUNDING_BOXES) {
            //g.setStroke(new BasicStroke(Config.BALL_BORDER_SIZE));
            //g.setColor(Config.BALL_BLACK_COLOR);
            //g.drawRect(x, y, width - Config.BALL_BORDER_SIZE, height - Config.BALL_BORDER_SIZE);

            g.setColor(Utility.invertColor(color));
            g.fillRect(x, y, width, height);
        }

        // Background
        g.setColor(color);
        g.fillOval(
                x + Config.BALL_BORDER_SIZE,
                y + Config.BALL_BORDER_SIZE,
                width - 2 * Config.BALL_BORDER_SIZE,
                height - 2 * Config.BALL_BORDER_SIZE
        );
    }

    private void drawStripes(Graphics2D g, PoolBall ball) {
        Vector2D position = ball.getPosition();

        int border = Config.BALL_BORDER_SIZE;

        double ballRadius = ball.getRadius();
        double radius = ballRadius - border;

        double s = radius * Math.cos(ANGLE);
        double t = radius * Math.sin(ANGLE);

        double x = position.x - s;
        double y1 = ball.getBounds().y + border;
        double y2 = position.y - 1;

        double width = 2 * s;
        double height = 2 * (radius - t);

        g.setColor(Config.BALL_WHITE_COLOR);
        g.fillArc((int) Math.round(x), (int) Math.round(y1), (int) Math.round(width), (int) Math.round(height), 0, 180);
        g.fillArc((int) Math.round(x), (int) Math.round(y2), (int) Math.round(width), (int) Math.round(height), 0, -180);
    }

    private void drawNumber(Graphics2D g, PoolBall ball) {
        // Number
        String number = ball.number + "";
        FontMetrics metrics = g.getFontMetrics(NUMBER_FONT);

        double x = ball.getPosition().x - metrics.stringWidth(number) / 2.0;
        double y = ball.getPosition().y + metrics.getHeight() / 3.0;

        g.setColor((ball.getColor() == Config.BALL_BLACK_COLOR) ? Config.BALL_WHITE_COLOR : Config.BALL_BLACK_COLOR);
        g.setFont(NUMBER_FONT);
        g.drawString(number, (float) x, (float) y);
    }

    private void drawAim(Graphics2D g, GameObject object) {
        Aimable aimable = (Aimable) object;

        if (aimable.isAiming()) {
            Vector2D objPosition = object.getPosition().clone();
            Vector2D aimPosition = aimable.getAimPosition();

            objPosition.multiply(2).subtract(aimPosition);

            g.setColor(Color.BLACK);
            g.drawLine((int) objPosition.x, (int) objPosition.y, (int) aimPosition.x, (int) aimPosition.y);
        }
    }
}
