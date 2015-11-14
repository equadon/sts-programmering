package labb4.game.initializers;

import labb4.game.Config;
import labb4.game.PoolBallFactory;
import labb4.game.Vector2D;
import labb4.game.objects.Ball;
import labb4.game.Table;
import labb4.game.objects.CueBall;
import labb4.game.objects.PoolBall;

public class SnookerInitializer implements BallInitializer {
    public static final int RED_BALLS = 15;

    @Override
    public PoolBall[] createBalls(Table table) {
        PoolBall[] balls = new PoolBall[RED_BALLS + 6];

        int radius = Config.BALL_RADIUS;
        int diameter = 2 * radius;

        createRedBalls(balls, table, radius, diameter);

        double yLine = 4 * table.height / 5.0;

        int i = RED_BALLS;

        Vector2D center = new Vector2D(table.getPlayableBounds().getCenterX(), table.getPlayableBounds().getCenterY());

        // Yellow ball
        Vector2D position = new Vector2D(center.x + 0.15 * table.width, yLine);
        balls[i] = PoolBallFactory.createSnookerBall(2, table, position, radius);
        i++;

        // Green ball
        position = new Vector2D(center.x - 0.15 * table.width, yLine);
        balls[i] = PoolBallFactory.createSnookerBall(3, table, position, radius);
        i++;

        // Brown ball
        position = new Vector2D(center.x, yLine);
        balls[i] = PoolBallFactory.createSnookerBall(4, table, position, radius);
        i++;

        // Blue ball
        position = new Vector2D(center.x, center.y);
        balls[i] = PoolBallFactory.createSnookerBall(5, table, position, radius);
        i++;

        // Pink ball
        position = new Vector2D(center.x, radius/2.0 + table.height / 3.0);
        balls[i] = PoolBallFactory.createSnookerBall(6, table, position, radius);
        i++;

        // Black ball
        position = new Vector2D(center.x, 0.125 * table.height);
        balls[i] = PoolBallFactory.createSnookerBall(7, table, position, radius);

        return balls;
    }

    private void createRedBalls(Ball[] balls, Table table, int radius, int diameter) {
        Vector2D position;

        double x = (table.getBounds().width / 2.0) - 4 * radius;
        double y = table.height / 3.0;

        int row = 1;
        int col = 0;
        for (int i = 0; i < RED_BALLS; i++) {
            if (col == row) {
                row++;
                col = 0;
            }

            position = new Vector2D(x + (5 - row) * radius + col * diameter, y - row * (diameter - 0.25 * radius));
            balls[i] = PoolBallFactory.createSnookerBall(1, table, position, radius);

            col++;
        }
    }

    @Override
    public CueBall createCueBall(Table table) {
        Vector2D position = new Vector2D(table.width / 2.0, 4.5 * table.height / 5.0);

        return new CueBall(table, position, Vector2D.zero(), Config.BALL_RADIUS);
    }
}
