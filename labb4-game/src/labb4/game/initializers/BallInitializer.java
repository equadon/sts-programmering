package labb4.game.initializers;

import labb4.game.Table;
import labb4.game.objects.CueBall;
import labb4.game.objects.PoolBall;

public interface BallInitializer {
    PoolBall[] createBalls(Table table);
    CueBall createCueBall(Table table);
}
