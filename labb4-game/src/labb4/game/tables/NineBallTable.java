package labb4.game.tables;

import labb4.game.*;
import labb4.game.handlers.NineBallHandler;
import labb4.game.objects.CueBall;
import labb4.game.objects.PoolBall;

import java.util.ArrayList;
import java.util.List;

public class NineBallTable extends Table {
    public NineBallTable(Player[] players) {
        super(Config.DEFAULT_TABLE_WIDTH, Config.DEFAULT_TABLE_HEIGHT, new NineBallHandler(), players);
    }

    @Override
    protected List<PoolBall> createBalls() {
        List<PoolBall> balls = new ArrayList<>();

        Integer[] numbers = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.shuffle(numbers);

        Utility.moveNumber(numbers, 9, 4);
        Utility.moveNumber(numbers, 1, 0);

        int radius = Config.BALL_RADIUS;
        int diameter = 2 * radius;

        Vector2D position;

        double x = (getBounds().width / 2.0) - 4 * radius;
        double y = height / 3.0;

        int direction = 1;
        int absRow = 1;
        int row = 1;
        int col = 0;
        for (int i = 0; i < 9; i++) {
            if (col == row) {
                if (col == 3) direction *= -1;
                row += direction;
                absRow++;
                col = 0;
            }

            int number = numbers[i];
            position = new Vector2D(x + (5 - row) * radius + col * diameter, y - absRow * (diameter - 0.25 * radius));
            balls.add(BallFactory.createStandardBall(number, this, position, radius));

            col++;
        }

        // Add cue ball
        Vector2D cueBallPosition = new Vector2D(width / 2.0, Config.DEFAULT_Y_LINE * getPlayableBounds().getMaxY() + Config.LINE_SIZE);
        balls.add(new CueBall(this, cueBallPosition, Config.BALL_RADIUS));

        return balls;
    }
}
