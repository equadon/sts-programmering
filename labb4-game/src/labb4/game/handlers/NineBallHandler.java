package labb4.game.handlers;

import labb4.game.Player;
import labb4.game.objects.Pocket;
import labb4.game.objects.PoolBall;

/**
 * This class handle nine ball rules.
 */
public class NineBallHandler extends GameHandler {
    @Override
    public void newGame() {
        System.out.println("new game");
    }

    @Override
    public void beginTurn(Player current) {
        System.out.println("begin turn");
    }

    @Override
    public void collision(PoolBall ball1, PoolBall ball2) {
        System.out.println("collision detected between " + ball1 + " and " + ball2);
    }

    @Override
    public void pocketed(PoolBall ball, Pocket pocket) {
        System.out.println(ball + " went in " + pocket);
    }

    @Override
    public void endTurn() {
        System.out.println("end turn");
    }
}
