package labb4.game.handlers;

import labb4.game.Player;
import labb4.game.objects.Pocket;
import labb4.game.objects.PoolBall;

/**
 * This class handle snooker rules.
 */
public class SnookerHandler extends GameHandler {
    @Override
    public void newGame(Player starting) {}

    @Override
    public void beginTurn(Player current) {}

    @Override
    public void collision(PoolBall ball1, PoolBall ball2) {}

    @Override
    public void pocketed(PoolBall ball, Pocket pocket) {}

    @Override
    public void endTurn(Player nextPlayer) {}
}
