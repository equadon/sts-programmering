package labb4.game.handlers;

import labb4.game.Player;
import labb4.game.interfaces.GameListener;
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
    private final List<GameListener> listeners;

    protected Map<PoolBall, Pocket> pocketedBalls;

    public GameHandler() {
        listeners = new ArrayList<>();
        pocketedBalls = new HashMap<>();
    }

    protected void add(PoolBall ball, Pocket pocket) {
        pocketedBalls.put(ball, pocket);
    }

    public void addListener(GameListener listener) {
        listeners.add(listener);
    }
    public void removeListener(GameListener listener) {
        listeners.remove(listener);
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
        for (GameListener o : listeners)
            o.illegalMove(reason);
    }

    protected void notifyPlayerChange(Player newPlayer) {
        for (GameListener o : listeners)
            o.changePlayer(newPlayer);
    }

    protected void notifyAddPoints(Player player, int points) {
        for (GameListener o : listeners)
            o.addPoints(player, points);
    }

    protected void notifyPlacingBall(Placeable placeable) {
        for (GameListener o : listeners)
            o.startPlacing(placeable);
    }

    protected void notifyGameOver(Player winner) {
        for (GameListener o : listeners)
            o.gameOver(winner);
    }
}
