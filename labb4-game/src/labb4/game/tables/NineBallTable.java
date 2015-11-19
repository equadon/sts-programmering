package labb4.game.tables;

import labb4.game.*;
import labb4.game.objects.CueBall;
import labb4.game.objects.Pocket;
import labb4.game.objects.PoolBall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class NineBallTable extends Table {
    private static final Logger LOG = Logger.getLogger(NineBallTable.class.getName());

    private Player player;
    private PoolBall firstHit;
    private PoolBall lowestBall;
    private Map<PoolBall, Pocket> pocketedBalls;

    public NineBallTable(Player[] players) {
        super(Config.DEFAULT_TABLE_WIDTH, Config.DEFAULT_TABLE_HEIGHT, players);

        pocketedBalls = new HashMap<>();
    }

    @Override
    public void newGame(Player starting) {}

    @Override
    public void beginTurn() {
        player = getCurrentPlayer();
        firstHit = null;
        pocketedBalls.clear();
        lowestBall = findLowestBall();
        System.out.println("begin turn (lowest ball = #" + lowestBall.number + ")");
    }

    @Override
    public void collision(PoolBall ball1, PoolBall ball2) {
        if (firstHit == null) {
            firstHit = (ball1 instanceof CueBall) ? ball2 : ball1;
        }
    }

    @Override
    public void pocketed(PoolBall ball, Pocket pocket) {
        pocketedBalls.put(ball, pocket);
    }

    @Override
    public void endTurn() {
        boolean validMove = false;

        int points = countPoints();

        if (isCueBallPocketed()) {
            notifyIllegalMove("Cue ball pocketed.");
            placeAllPocketedBalls();
        } else if (firstHit == null) {
            System.out.println("No balls hit, change player.");
            nextPlayer();
            notifyPlayerChange(getCurrentPlayer());
        } else if (firstHit != lowestBall) {
            notifyIllegalMove("Must hit the lowest ball first.");
            placeAllPocketedBalls();
        } else if (isBallPocketed(9)) {
            notifyGameOver(getCurrentPlayer());
        } else if (pocketedBalls.size() == 0) {
            System.out.println("Failed to pocket a ball, change player.");
            nextPlayer();
            notifyPlayerChange(getCurrentPlayer());
        } else {
            System.out.println("OK, keep playing.");
            validMove = true;
        }

        if (validMove && points > 0) {
            notifyAddPoints(player, points);
        }
    }

    private int countPoints() {
        int points = 0;
        for (Map.Entry<PoolBall, Pocket> entry : pocketedBalls.entrySet()) {
            points += entry.getKey().number;
        }

        return points;
    }

    private void placeAllPocketedBalls() {
        for (Map.Entry<PoolBall, Pocket> entry : pocketedBalls.entrySet()) {
            PoolBall ball = entry.getKey();
            Pocket pocket = entry.getValue();

            pocket.remove(ball);

            notifyPlacingBall(ball);
        }
    }

    private PoolBall findLowestBall() {
        PoolBall lowest = getBalls()[0];
        for (PoolBall ball : getBalls()) {
            if (!(ball instanceof CueBall) && ball.number < lowest.number) {
                lowest = ball;
            }
        }
        return lowest;
    }

    private boolean isCueBallPocketed() {
        for (Map.Entry<PoolBall, Pocket> entry : pocketedBalls.entrySet()) {
            if (entry.getKey() instanceof CueBall) {
                return true;
            }
        }
        return false;
    }

    private boolean isBallPocketed(int number) {
        for (Map.Entry<PoolBall, Pocket> entry : pocketedBalls.entrySet()) {
            if (entry.getKey().number == number) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected List<PoolBall> createBalls() {
        List<PoolBall> balls = new ArrayList<>();

        Integer[] numbers = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};

        Utility.shuffle(numbers);
        Utility.moveNumber(numbers, 9, 4);
        Utility.moveNumber(numbers, 1, 0);

        int radius = Config.BALL_RADIUS;
        int diameter = 2 * radius;

        Vector2D position;

        double x = 2 * getBounds().width / 3.0;
        double y = (getBounds().height / 2.0) - 4 * radius;

        int direction = 1;
        int absCol = 1;
        int col = 1;
        int row = 0;
        for (int i = 0; i < 9; i++) {
            if (col == row) {
                if (col == 3) direction *= -1;
                col += direction;
                absCol++;
                row = 0;
            }

            int number = numbers[i];
            position = new Vector2D(x + absCol * (diameter - 0.25*radius), y + (5 - col) * radius + row * diameter);
            balls.add(BallFactory.createStandardBall(number, this, position, radius));

            row++;
        }

        // Add cue ball
        Vector2D cueBallPosition = new Vector2D(Config.DEFAULT_X_LINE * getPlayableBounds().getMaxX() + Config.LINE_SIZE, height / 2.0);
        balls.add(new CueBall(this, cueBallPosition, Config.BALL_RADIUS));

        return balls;
    }
}
