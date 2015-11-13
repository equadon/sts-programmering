package labb4.game.initializers;

import labb4.game.Config;
import labb4.game.Vector2D;
import labb4.game.objects.Ball;
import labb4.game.Table;
import labb4.game.objects.CueBall;

public class EightBallInitializer implements BallInitializer {
    @Override
    public Ball[] createBalls(Table table) {
        return new Ball[0];
    }

    @Override
    public CueBall createCueBall(Table table) {
        Vector2D position = new Vector2D(table.width / 2.0, 3 * table.height / 4.0);

        return new CueBall(table, position, Vector2D.createZero(), Config.BALL_RADIUS);
    }
}
