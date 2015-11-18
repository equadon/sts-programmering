package labb4.game.tables;

import labb4.game.*;
import labb4.game.objects.CueBall;
import labb4.game.objects.Pocket;
import labb4.game.objects.PoolBall;

import java.util.ArrayList;
import java.util.List;

public class NineBallTable extends Table {
    public NineBallTable(Player[] players) {
        super(Config.DEFAULT_TABLE_WIDTH, Config.DEFAULT_TABLE_HEIGHT, players);
    }

    @Override
    public void newGame(Player starting) {}

    @Override
    public void beginTurn() {}

    @Override
    public void collision(PoolBall ball1, PoolBall ball2) {}

    @Override
    public void pocketed(PoolBall ball, Pocket pocket) {}

    @Override
    public void endTurn() {}

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

        double x = 2 * getBounds().width / 3.0;
        double y = (getBounds().height / 2.0) - 4 * radius;

        int direction = 1;
        int absCol = 1;
        int col = 1;
        int row = 0;
        for (int i = 0; i < 9; i++) {
            if (col == row) {
                if (col == 3) direction *= -1;
                col += direction;
                absCol++;
                row = 0;
            }

            int number = numbers[i];
            position = new Vector2D(x + absCol * (diameter - 0.25*radius), y + (5 - col) * radius + row * diameter);
            balls.add(BallFactory.createStandardBall(number, this, position, radius));

            row++;
        }

        // Add cue ball
        Vector2D cueBallPosition = new Vector2D(Config.DEFAULT_X_LINE * getPlayableBounds().getMaxX() + Config.LINE_SIZE, height / 2.0);
        balls.add(new CueBall(this, cueBallPosition, Config.BALL_RADIUS));

        return balls;
    }
}
