package labb4.game.handlers;

import labb4.game.Player;
import labb4.game.objects.CueBall;
import labb4.game.objects.Pocket;
import labb4.game.objects.PoolBall;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles nine ball rules.
 */
public class NineBallHandler extends GameHandler {
    private int turn;

    private PoolBall firstHit;
    private PoolBall targetBall;

    public NineBallHandler() {
        pocketedBalls = new ArrayList<>();
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
        turn++;

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
                notifyGameWinner();
            }
        }
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
            target = balls[0];

            for (int i = 1; i < balls.length; i++) {
                if (balls[i].points < target.points) {
                    target = balls[i];
                }
            }
        }

        return target;
    }
}
