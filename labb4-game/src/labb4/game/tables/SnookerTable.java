package labb4.game.tables;

import labb4.game.*;
import labb4.game.objects.CueBall;
import labb4.game.objects.Pocket;
import labb4.game.objects.PoolBall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnookerTable extends Table {
    public static final int RED_BALL_COUNT = 15;

    private Player player;
    private Map.Entry<PoolBall, Pocket> firstPocketed;
    private boolean nextRed;
    private PoolBall lowestBall;

    public SnookerTable(Player[] players) {
        super(Config.SNOOKER_TABLE_WIDTH, Config.SNOOKER_TABLE_HEIGHT, players);
    }

    @Override
    public void newGame(Player starting) {
        notifyPlayerChange(starting);
        notifyNextBall("Red");
        nextRed = true;
    }

    @Override
    public void beginTurn() {
        pocketedBalls.clear();
        firstPocketed = null;
        player = getCurrentPlayer();

        if (countRedBalls() == 0) {
            lowestBall = findLowestBall();
        }
    }

    @Override
    public void collision(PoolBall ball1, PoolBall ball2) {}

    @Override
    public void pocketed(PoolBall ball, Pocket pocket) {
        pocketedBalls.put(ball, pocket);

        if (firstPocketed == null) {
            firstPocketed = new HashMap.SimpleEntry<>(ball, pocket);
        }
    }

    @Override
    public void endTurn() {
        boolean gameOver = false;
        boolean validMove = false;

        PoolBall firstBall = (firstPocketed == null) ? null : firstPocketed.getKey();

        int redCount = countRedBalls();

        if (isCueBallPocketed()) {
            notifyIllegalMove("Not allowed to pocket the cue ball");
            changePlayer();
            placeAllPocketedBalls();
            nextRed = true;
        } else if (firstBall != null && lowestBall != null && firstBall != lowestBall) {
            notifyIllegalMove(firstBall.number + " is not the lowest ball.");
            changePlayer();
            placeAllNonRedPocketedBalls();
            nextRed = true;
        } else if (firstBall != null && redCount != 0 && ((nextRed && firstBall.number != 1) || (!nextRed && firstBall.number == 1))) {
            notifyIllegalMove("Incorrect ball type pocketed.");
            changePlayer();
            placeAllNonRedPocketedBalls();
            nextRed = true;
        } else if (redCount == 0 && isBallPocketed(7)) {
            calcPoints();

            notifyNextBall("-");
            notifyGameOver(findWinner());
            validMove = true;
            gameOver = true;
        } else if (pocketedBalls.size() == 0) {
            changePlayer();
            nextRed = true;
        } else if (redCount > 0 && !nextRed && firstBall != null && firstBall.number > 1) {
            placeAllNonRedPocketedBalls();
            validMove = true;
        } else {
            validMove = true;
        }

        if (!gameOver && validMove) {
            nextRed = !nextRed;

            calcPoints();
        }

        if (!gameOver) {
            String nextBall;
            if (countRedBalls() == 0)
                nextBall = "#" + findLowestBall().number;
            else
                nextBall = nextRed ? "Red" : "Colored";
            notifyNextBall(nextBall);
        }
    }

    private void calcPoints() {
        int points = countPoints();
        player.addPoints(points);
        notifyAddPoints(player, points);
    }

    private Player findWinner() {
        Player winner = null;

        for (Player player : getPlayers()) {
            if (winner == null) {
                winner = player;
            } else if (player.getPoints() > winner.getPoints()) {
                winner = player;
            }
        }

        return winner;
    }

    private int countPoints() {
        int points = 0;
        for (Map.Entry<PoolBall, Pocket> entry : pocketedBalls.entrySet()) {
            points += entry.getKey().number;
        }

        return points;
    }

    private PoolBall findLowestBall() {
        PoolBall lowest = null;
        for (PoolBall ball : balls) {
            if (ball.number > 1) {
                if (lowest == null)
                    lowest = ball;
                else if (ball.number < lowest.number)
                    lowest = ball;
            }
        }

        return lowest;
    }

    private int countRedBalls() {
        int count = 0;
        for (PoolBall ball : balls) {
            if (!(ball instanceof CueBall) && ball.number == 1) {
                count++;
            }
        }

        return count;
    }

    private void changePlayer() {
        nextRed = true;
        nextPlayer();
        notifyPlayerChange(getCurrentPlayer());
    }

    protected void placeAllNonRedPocketedBalls() {
        for (Map.Entry<PoolBall, Pocket> entry : pocketedBalls.entrySet()) {
            PoolBall ball = entry.getKey();
            Pocket pocket = entry.getValue();

            if (ball.number == 1)
                continue;

            pocket.remove(ball);

            notifyPlacingBall(ball);
        }
    }

    @Override
    protected List<PoolBall> createBalls() {
        List<PoolBall> balls = new ArrayList<>();

        int radius = Config.BALL_RADIUS;
        int diameter = 2 * radius;

        createRedBalls(balls, radius, diameter);

        double xLine = Config.SNOOKER_X_LINE * getPlayableBounds().getMaxX();

        Vector2D center = new Vector2D(getPlayableBounds().getCenterX(), getPlayableBounds().getCenterY());

        // Yellow ball
        Vector2D position = new Vector2D(xLine, center.y + 0.15 * height);
        balls.add(BallFactory.createSnookerBall(2, this, position, radius));

        // Green ball
        position = new Vector2D(xLine, center.y - 0.15 * height);
        balls.add(BallFactory.createSnookerBall(3, this, position, radius));

        // Brown ball
        position = new Vector2D(xLine, center.y);
        balls.add(BallFactory.createSnookerBall(4, this, position, radius));

        // Blue ball
        position = new Vector2D(center.x, center.y);
        balls.add(BallFactory.createSnookerBall(5, this, position, radius));

        // Pink ball
        position = new Vector2D(2 * width / 3.0 - radius / 4.0, center.y);
        balls.add(BallFactory.createSnookerBall(6, this, position, radius));

        // Black ball
        position = new Vector2D((1-0.125) * width, center.y);
        balls.add(BallFactory.createSnookerBall(7, this, position, radius));

        Vector2D cueBallPosition = new Vector2D(xLine + Config.LINE_SIZE - 0.15 * height, height / 2.0);
        balls.add(new CueBall(this, cueBallPosition, Config.BALL_RADIUS));

        return balls;
    }

    private void createRedBalls(List<PoolBall> balls, int radius, int diameter) {
        Vector2D position;

        double x = 2 * getBounds().width / 3.0;
        double y = (getBounds().height / 2.0) - 4 * radius;

        int col = 1;
        int row = 0;
        for (int i = 0; i < RED_BALL_COUNT; i++) {
            if (col == row) {
                col++;
                row = 0;
            }

            position = new Vector2D(x + col * (diameter - 0.25*radius), y + (5 - col) * radius + row * diameter);
            balls.add(BallFactory.createSnookerBall(1, this, position, radius));

            row++;
        }
    }
}
