package labb4.game.tables;

import labb4.game.*;
import labb4.game.handlers.EightBallHandler;
import labb4.game.objects.CueBall;
import labb4.game.objects.PoolBall;

import java.util.ArrayList;
import java.util.List;

public class EightBallTable extends Table {
    public EightBallTable(Player player1, Player player2) {
        super(Config.DEFAULT_TABLE_WIDTH, Config.DEFAULT_TABLE_HEIGHT, new EightBallHandler(), player1, player2);
    }

    @Override
    protected List<PoolBall> createBalls() {
        List<PoolBall> balls = new ArrayList<>();

        Integer[] numbers = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        Utility.shuffle(numbers);
        Utility.moveNumber(numbers, 8, 4);

        int radius = Config.BALL_RADIUS;
        int diameter = 2 * radius;

        Vector2D position;

        double x = (getBounds().width / 2.0) - 4 * radius;
        double y = height / 3.0;

        int row = 1;
        int col = 0;
        for (int i = 0; i < 15; i++) {
            if (col == row) {
                row++;
                col = 0;
            }

            int number = numbers[i];
            position = new Vector2D(x + (5 - row) * radius + col * diameter, y - row * (diameter - 0.25 * radius));
            balls.add(BallFactory.createStandardBall(number, this, position, radius));

            col++;
        }

        // Add cue ball
        Vector2D cueBallPosition = new Vector2D(width / 2.0, 3 * height / 4.0);
        balls.add(new CueBall(this, cueBallPosition, Config.BALL_RADIUS));

        return balls;
    }
}
