package labb4.game.initializers;

import labb4.game.objects.Ball;
import labb4.game.Table;
import labb4.game.objects.CueBall;

public class SnookerInitializer implements BallInitializer {
    @Override
    public Ball[] createBalls(Table table) {
        return new Ball[0];
    }

    @Override
    public CueBall createCueBall(Table table) {
        return null;
    }
}
