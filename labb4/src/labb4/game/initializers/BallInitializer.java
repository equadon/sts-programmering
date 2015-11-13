package labb4.game.initializers;

import labb4.game.objects.Ball;
import labb4.game.Table;
import labb4.game.objects.CueBall;

public interface BallInitializer {
    Ball[] createBalls(Table table);
    CueBall createCueBall(Table table);
}
