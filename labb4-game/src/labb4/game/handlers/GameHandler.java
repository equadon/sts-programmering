package labb4.game.handlers;

import javafx.util.Pair;
import labb4.game.Player;
import labb4.game.interfaces.GameObserver;
import labb4.game.interfaces.Placeable;
import labb4.game.objects.Pocket;
import labb4.game.objects.PoolBall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that handle game rules.
 */
public abstract class GameHandler {
    private final List<GameObserver> observers;

    protected Map<PoolBall, Pocket> pocketedBalls;

    public GameHandler() {
        observers = new ArrayList<>();
        pocketedBalls = new HashMap<>();
    }

    protected void add(PoolBall ball, Pocket pocket) {
        pocketedBalls.put(ball, pocket);
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

    protected void notifyPlacingBall(Placeable placeable) {
        for (GameObserver o : observers)
            o.startPlacing(placeable);
    }

    protected void notifyGameOver(Player winner) {
        for (GameObserver o : observers)
            o.gameOver(winner);
    }
}
