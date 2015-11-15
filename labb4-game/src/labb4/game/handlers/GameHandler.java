package labb4.game.handlers;

import labb4.game.Player;
import labb4.game.Table;
import labb4.game.interfaces.GameListener;
import labb4.game.objects.CueBall;
import labb4.game.objects.Pocket;
import labb4.game.objects.PoolBall;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles all game rules.
 */
public abstract class GameHandler {
    private final List<GameListener> listeners;
    protected Table table;

    protected List<PoolBall> pocketedBalls;

    public GameHandler() {
        listeners = new ArrayList<>();
        pocketedBalls = new ArrayList<>();
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void addListener(GameListener listener) {
        listeners.add(listener);
    }
    public void removeListener(GameListener listener) {
        listeners.remove(listener);
    }

    protected boolean isCueBallPocketed() {
        for (PoolBall ball : pocketedBalls) {
            if (ball instanceof CueBall) {
                return true;
            }
        }

        return false;
    }

    protected boolean isBallPocketed(int number) {
        for (PoolBall ball : pocketedBalls) {
            if (ball.points == number) {
                return true;
            }
        }

        return false;
    }

    protected int calculatePocketedPoints() {
        int points = 0;

        for (PoolBall ball : pocketedBalls) {
            points += ball.points;
        }

        return points;
    }

    public void ballPocketed(Pocket pocket, PoolBall ball) {
        pocketedBalls.add(ball);
    }

    public void removedFromPocket(Pocket pocket, PoolBall ball) {
        pocketedBalls.remove(ball);
    }

    /**
     * Trigger methods when certain events happen in-game.
     */
    public abstract void beginTurn(Player player);
    public abstract void collision(PoolBall ball1, PoolBall ball2);
    public abstract void endTurn();

    /**
     * Notification methods.
     */
    protected void notifyInvalidMove(String reason) {
        for (GameListener listener : listeners)
            listener.illegalMove(reason);
    }

    protected void notifyPlayerChange(Player currentPlayer) {
        for (GameListener listener : listeners)
            listener.changedPlayer(currentPlayer);
    }

    protected void notifyGameWinner() {
        for (GameListener listener : listeners)
            listener.gameOver(table.getCurrentPlayer());
    }

    protected void notifyAddPoints(int points) {
        for (GameListener listener : listeners)
            listener.addPoints(table.getCurrentPlayer(), points);
    }
}
