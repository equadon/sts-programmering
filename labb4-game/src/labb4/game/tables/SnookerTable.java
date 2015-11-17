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

        createRedBalls(balls, this, radius, diameter);

        double yLine = Config.SNOOKER_Y_LINE * getPlayableBounds().getMaxY();

        Vector2D center = new Vector2D(getPlayableBounds().getCenterX(), getPlayableBounds().getCenterY());

        // Yellow ball
        Vector2D position = new Vector2D(center.x + 0.15 * width, yLine);
        balls.add(BallFactory.createSnookerBall(2, this, position, radius));

        // Green ball
        position = new Vector2D(center.x - 0.15 * width, yLine);
        balls.add(BallFactory.createSnookerBall(3, this, position, radius));

        // Brown ball
        position = new Vector2D(center.x, yLine);
        balls.add(BallFactory.createSnookerBall(4, this, position, radius));

        // Blue ball
        position = new Vector2D(center.x, center.y);
        balls.add(BallFactory.createSnookerBall(5, this, position, radius));

        // Pink ball
        position = new Vector2D(center.x, radius/2.0 + height / 3.0);
        balls.add(BallFactory.createSnookerBall(6, this, position, radius));

        // Black ball
        position = new Vector2D(center.x, 0.125 * height);
        balls.add(BallFactory.createSnookerBall(7, this, position, radius));

        Vector2D cueBallPosition = new Vector2D(width / 2.0, yLine + Config.LINE_SIZE + 5*Config.BALL_RADIUS);
        balls.add(new CueBall(this, cueBallPosition, Config.BALL_RADIUS));

        return balls;
    }

    private void createRedBalls(List<PoolBall> balls, Table table, int radius, int diameter) {
        Vector2D position;

        double x = (table.getBounds().width / 2.0) - 4 * radius;
        double y = table.height / 3.0;

        int row = 1;
        int col = 0;
        for (int i = 0; i < RED_BALL_COUNT; i++) {
            if (col == row) {
                row++;
                col = 0;
            }

            position = new Vector2D(x + (5 - row) * radius + col * diameter, y - row * (diameter - 0.25 * radius));
            balls.add(BallFactory.createSnookerBall(1, table, position, radius));

            col++;
        }
    }
}
