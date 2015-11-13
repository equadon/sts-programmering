package labb4.game.initializers;

import labb4.game.objects.Ball;
import labb4.game.Table;
import labb4.game.objects.CueBall;
import labb4.game.objects.PoolBall;

public class SnookerInitializer implements BallInitializer {
    @Override
    public PoolBall[] createBalls(Table table) {
        return new PoolBall[0];
    }

    @Override
    public CueBall createCueBall(Table table) {
        return null;
    }
}
