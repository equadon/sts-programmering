package labb4.game.handlers;

import labb4.game.Player;
import labb4.game.interfaces.GameObserver;
import labb4.game.objects.Pocket;
import labb4.game.objects.PoolBall;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles all game rules.
 */
public abstract class GameHandler {
    private final List<GameObserver> observers;

    protected List<PoolBall> pocketedBalls;

    public GameHandler() {
        observers = new ArrayList<>();
        pocketedBalls = new ArrayList<>();
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }
    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    public abstract void newGame();
    public abstract void beginTurn(Player current);
    public abstract void collision(PoolBall ball1, PoolBall ball2);
    public abstract void pocketed(Pocket pocket, PoolBall ball);
    public abstract void endTurn();
    public abstract void gameOver();
}
