package labb4.game.handlers;

import labb4.game.Player;
import labb4.game.objects.Pocket;
import labb4.game.objects.PoolBall;
import labb4.game.tables.Table;

/**
 * This class handles nine ball rules.
 */
public class EightBallHandler extends GameHandler {
    @Override
    public void newGame() {}

    @Override
    public void beginTurn(Player current) {}

    @Override
    public void collision(PoolBall ball1, PoolBall ball2) {}

    @Override
    public void pocketed(Pocket pocket, PoolBall ball) {}

    @Override
    public void endTurn() {}

    @Override
    public void gameOver() {}
}
