package labb4.game.initializers;

import labb4.game.Config;
import labb4.game.PoolBallFactory;
import labb4.game.Table;
import labb4.game.Vector2D;
import labb4.game.objects.CueBall;
import labb4.game.objects.PoolBall;

import java.util.Arrays;
import java.util.Random;

public class NineBallInitializer implements BallInitializer {
    @Override
    public PoolBall[] createBalls(Table table) {
        Integer[] numbers = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        shuffle(numbers);

        PoolBall[] balls = new PoolBall[numbers.length];

        int radius = Config.BALL_RADIUS;
        int diameter = 2 * radius;

        Vector2D position;

        double x = (table.getBounds().width / 2.0) - 4 * radius;
        double y = table.height / 3.0;

        int direction = 1;
        int absRow = 1;
        int row = 1;
        int col = 0;
        for (int i = 0; i < balls.length; i++) {
            if (col == row) {
                if (col == 3) direction *= -1;
                row += direction;
                absRow++;
                col = 0;
            }

            int number = numbers[i];
            position = new Vector2D(x + (5 - row) * radius + col * diameter, y - absRow * (diameter - 0.25 * radius));
            balls[i] = PoolBallFactory.createStandardBall(number, table, position, radius);

            col++;
        }

        return balls;
    }

    @Override
    public CueBall createCueBall(Table table) {
        Vector2D position = new Vector2D(table.width / 2.0, 3 * table.height / 4.0);

        return new CueBall(table, position, Vector2D.zero(), Config.BALL_RADIUS);
    }

    /**
     * Fisher-Yates shuffle.
     */
    private void shuffle(Integer[] array) {
        int index, temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}
