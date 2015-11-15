package labb4.game.handlers;

import labb4.game.Player;
import labb4.game.objects.CueBall;
import labb4.game.objects.PoolBall;

import java.util.ArrayList;

/**
 * This class handles nine ball rules.
 */
public class NineBallHandler extends GameHandler {
    private boolean isGameOver;
    private PoolBall firstHit;
    private PoolBall targetBall;

    public NineBallHandler() {
        pocketedBalls = new ArrayList<>();
    }

    @Override
    public void newGame() {
        notifyPlayerChange(table.getCurrentPlayer());
        updateTurnText();
        updateMessage();
    }

    @Override
    public void beginTurn(Player player) {
        firstHit = null;
        targetBall = findTargetBall();
        pocketedBalls.clear();
    }

    @Override
    public void collision(PoolBall ball1, PoolBall ball2) {
        if (firstHit == null && ball1 instanceof CueBall) {
            firstHit = ball2;
        }
    }

    @Override
    public void endTurn() {
        // Invalid move if target ball was not hit first
        if (isCueBallPocketed()) {
            illegalMove("Cue ball cannot be pocketed.");
        } else if (firstHit == null) {
            illegalMove("No ball collision.");
        } else if (firstHit != targetBall) {
            illegalMove(String.format("Ball (%d) was the target ball but ball (%d) was hit first.", firstHit.points, targetBall.points));
        } else { // legal move
            // add points for all pocketed balls
            notifyAddPoints(calculatePocketedPoints());

            if (pocketedBalls.size() == 0) {
                table.changeTurn();
                notifyPlayerChange(table.getCurrentPlayer());
            } else if (isBallPocketed(9)) {
                // won game!
                isGameOver = true;
                notifyGameWinner();
            }
        }

        if (!isGameOver) {
            updateTurnText();
            updateMessage();
        }
    }

    private void updateMessage() {
        notifyMessageUpdate("Next ball: " + findTargetBall().points);
    }

    private void updateTurnText() {
        notifyTurnTextUpdate(table.getCurrentPlayer().name + "' turn!");
    }

    private void illegalMove(String reason) {
        notifyInvalidMove(reason);
        table.changeTurn();
        notifyPlayerChange(table.getCurrentPlayer());
    }

    private PoolBall findTargetBall() {
        PoolBall[] balls = table.getBalls();
        PoolBall target = null;

        if (balls.length > 0) {
            for (int i = 0; i < balls.length; i++) {
                if (balls[i].points < 1) continue;

                if (target == null) {
                    target = balls[i];
                } else if (balls[i].points < target.points) {
                    target = balls[i];
                }
            }
        }

        return target;
    }
}
