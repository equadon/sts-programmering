package labb4.game.tables;

import labb4.game.*;
import labb4.game.handlers.SnookerHandler;
import labb4.game.objects.CueBall;
import labb4.game.objects.PoolBall;

import java.util.ArrayList;
import java.util.List;

public class SnookerTable extends Table {
    public static final int RED_BALL_COUNT = 15;

    public SnookerTable(Player[] players) {
        super(Config.SNOOKER_TABLE_WIDTH, Config.SNOOKER_TABLE_HEIGHT, new SnookerHandler(), players);
    }

    @Override
    protected List<PoolBall> createBalls() {
        List<PoolBall> balls = new ArrayList<>();

        int radius = Config.BALL_RADIUS;
        int diameter = 2 * radius;

        createRedBalls(balls, radius, diameter);

        double xLine = Config.SNOOKER_X_LINE * getPlayableBounds().getMaxX();

        Vector2D center = new Vector2D(getPlayableBounds().getCenterX(), getPlayableBounds().getCenterY());

        // Yellow ball
        Vector2D position = new Vector2D(xLine, center.y + 0.15 * height);
        balls.add(BallFactory.createSnookerBall(2, this, position, radius));

        // Green ball
        position = new Vector2D(xLine, center.y - 0.15 * height);
        balls.add(BallFactory.createSnookerBall(3, this, position, radius));

        // Brown ball
        position = new Vector2D(xLine, center.y);
        balls.add(BallFactory.createSnookerBall(4, this, position, radius));

        // Blue ball
        position = new Vector2D(center.x, center.y);
        balls.add(BallFactory.createSnookerBall(5, this, position, radius));

        // Pink ball
        position = new Vector2D(2 * width / 3.0 - radius / 4.0, center.y);
        balls.add(BallFactory.createSnookerBall(6, this, position, radius));

        // Black ball
        position = new Vector2D((1-0.125) * width, center.y);
        balls.add(BallFactory.createSnookerBall(7, this, position, radius));

        Vector2D cueBallPosition = new Vector2D(xLine + Config.LINE_SIZE - 0.15 * height, height / 2.0);
        balls.add(new CueBall(this, cueBallPosition, Config.BALL_RADIUS));

        return balls;
    }

    private void createRedBalls(List<PoolBall> balls, int radius, int diameter) {
        Vector2D position;

        double x = 2 * getBounds().width / 3.0;
        double y = (getBounds().height / 2.0) - 4 * radius;

        int col = 1;
        int row = 0;
        for (int i = 0; i < 15; i++) {
            if (col == row) {
                col++;
                row = 0;
            }

            position = new Vector2D(x + col * (diameter - 0.25*radius), y + (5 - col) * radius + row * diameter);
            balls.add(BallFactory.createSnookerBall(1, this, position, radius));

            row++;
        }
    }
}
