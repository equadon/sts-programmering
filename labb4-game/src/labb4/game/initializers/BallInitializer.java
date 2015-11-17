package labb4.game.initializers;

import labb4.game.Table;
import labb4.game.objects.CueBall;
import labb4.game.objects.PoolBall;

import java.util.List;

public interface BallInitializer {
    List<PoolBall> createBalls(Table table);
}
