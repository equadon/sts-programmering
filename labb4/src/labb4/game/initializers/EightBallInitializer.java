package labb4.game.initializers;

import labb4.game.PoolBallFactory;
import labb4.game.Config;
import labb4.game.Vector2D;
import labb4.game.objects.Ball;
import labb4.game.Table;
import labb4.game.objects.CueBall;
import labb4.game.objects.PoolBall;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EightBallInitializer implements BallInitializer {
    @Override
    public PoolBall[] createBalls(Table table) {
        Integer[] numbers = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        shuffle(numbers);

        PoolBall[] balls = new PoolBall[numbers.length];

        int radius = Config.BALL_RADIUS;
        int diameter = 2 * radius;

        Vector2D position;

        double x = 100;
        double y = 300;

        int row = 1;
        int col = 0;
        for (int i = 0; i < balls.length; i++) {
            if (col == row) {
                row++;
                col = 0;
            }

            int number = numbers[i];

            position = new Vector2D(x + (5 - row) * radius + col * diameter, y - row * (diameter - radius*0.2));

            balls[i] = PoolBallFactory.createBall(number, table, position, radius);

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

        // Make sure number 8 is at position 4
        int indexEight = Arrays.asList(array).indexOf(8);
        if (indexEight != 4) {
            array[indexEight] = array[4];
            array[4] = 8;
        }
    }
}
