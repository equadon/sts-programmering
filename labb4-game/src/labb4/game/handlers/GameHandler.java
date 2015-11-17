package labb4.game.handlers;

import labb4.game.Player;
import labb4.game.interfaces.GameObserver;
import labb4.game.interfaces.Placeable;
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

    public abstract void newGame(Player starting);
    public abstract void beginTurn(Player current);
    public abstract void collision(PoolBall ball1, PoolBall ball2);
    public abstract void pocketed(PoolBall ball, Pocket pocket);
    public abstract void endTurn(Player nextPlayer);

    /**
     * Notification methods.
     */
    protected void notifyIllegalMove(String reason) {
        for (GameObserver o : observers)
            o.illegalMove(reason);
    }

    protected void notifyPlayerChange(Player newPlayer) {
        for (GameObserver o : observers)
            o.changePlayer(newPlayer);
    }

    protected void notifyAddPoints(Player player, int points) {
        for (GameObserver o : observers)
            o.addPoints(player, points);
    }

    protected void notifyUpdateTopMessage(String text) {
        for (GameObserver o : observers)
            o.updateMessageTop(text);
    }

    protected void notifyUpdateBottomMessage(String text) {
        for (GameObserver o : observers)
            o.updateMessageBottom(text);
    }

    protected void notifyPlacingBall(Placeable placeable) {
        for (GameObserver o : observers)
            o.startPlacing(placeable);
    }

    protected void notifyGameOver(Player winner) {
        for (GameObserver o : observers)
            o.gameOver(winner);
    }
}
