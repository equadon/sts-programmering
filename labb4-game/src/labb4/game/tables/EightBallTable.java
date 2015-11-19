package labb4.game.tables;

import labb4.game.*;
import labb4.game.objects.CueBall;
import labb4.game.objects.Pocket;
import labb4.game.objects.PoolBall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EightBallTable extends Table {
    private Player player;
    private Player stripedBalls;
    private PoolBall firstPocketed;

    public EightBallTable(Player[] players) {
        super(Config.DEFAULT_TABLE_WIDTH, Config.DEFAULT_TABLE_HEIGHT, players);

        pocketedBalls = new HashMap<>();
    }

    @Override
    public void newGame(Player starting) {
        player = null;
        stripedBalls = null;
        firstPocketed = null;

        for (Player player : getPlayers())
            player.reset();

        notifyPlayerChange(starting);

        notifyNextBall("Any");
    }

    @Override
    public void beginTurn() {
        player = getCurrentPlayer();
        firstPocketed = null;
        pocketedBalls.clear();
    }

    @Override
    public void collision(PoolBall ball1, PoolBall ball2) {}

    @Override
    public void pocketed(PoolBall ball, Pocket pocket) {
        if (firstPocketed == null) {
            firstPocketed = ball;
        }

        pocketedBalls.put(ball, pocket);
    }

    @Override
    public void endTurn() {
        boolean gameOver = false;
        boolean validMove = false;

        if (isCueBallPocketed()) {
            notifyIllegalMove("Not allowed to pocket the cue ball.");
            placeAllPocketedBalls();
        } else if (isBallPocketed(8) && hasBallsLeft(player)) {
            notifyIllegalMove("Can't pocket 8 ball until all own balls are pocketed.");
            placeAllPocketedBalls();
            changePlayer();
        } else if (firstPocketed != null && stripedBalls == null) {
            stripedBalls = (firstPocketed.striped) ? player : getOtherPlayer();

            System.out.println("Striped balls now belong to: " + stripedBalls.name);
        } else if (isBallPocketed(8)) {
            gameOver = true;
            validMove = true;
            notifyGameOver(player);
        } else if (countOwnPocketedBalls(player) <= 0) {
            changePlayer();
        } else {
            validMove = true;
        }

        int points = calculatePoints();

        int count = countOwnBalls(getCurrentPlayer());

        if (getCurrentPlayer() == stripedBalls) {
            notifyNextBall((count == 0) ? "Eight ball" : "Striped color");
        } else if (stripedBalls != null) {
            notifyNextBall((count == 0) ? "Eight ball" : "Solid color");
        }

        if (validMove && points > 0) {
            player.addPoints(points);
            notifyAddPoints(player, points);
        }
    }

    private void changePlayer() {
        nextPlayer();
        notifyPlayerChange(getCurrentPlayer());
    }

    private boolean isStriped() {
        return player == stripedBalls;
    }

    private int calculatePoints() {
        if (stripedBalls == null)
            return 0;

        int points = 0;

        for (Map.Entry<PoolBall, Pocket> entry : pocketedBalls.entrySet()) {
            PoolBall ball = entry.getKey();
            if (ball instanceof CueBall)
                continue;

            if (isStriped() && ball.striped) {
                points += ball.number;
            } else if (!isStriped() && !ball.striped) {
                points += ball.number;
            }
        }

        return points;
    }

    private int countOwnPocketedBalls(Player currentPlayer) {
        if (stripedBalls == null)
            return 0;

        int count = 0;
        boolean isStriped = currentPlayer == stripedBalls;

        for (Map.Entry<PoolBall, Pocket> entry : pocketedBalls.entrySet()) {
            PoolBall ball = entry.getKey();
            if (ball.number == 8 || ball instanceof CueBall)
                continue;

            if ((isStriped&& ball.striped) || (!isStriped && !ball.striped)) {
                count++;
            }
        }

        return count;
    }

    private int countOwnBalls(Player currentPlayer) {
        if (stripedBalls == null)
            return 0;

        int count = 0;

        boolean isStriped = currentPlayer == stripedBalls;

        for (PoolBall ball : balls) {
            if (ball.number == 8 || ball instanceof CueBall)
                continue;

            if ((isStriped && ball.striped) || (!isStriped && !ball.striped)) {
                count++;
            }
        }

        return count;
    }

    private boolean hasBallsLeft(Player currentPlayer) {
        if (stripedBalls == null)
            return true;

        boolean isStriped = currentPlayer == stripedBalls;

        for (PoolBall ball : balls) {
            if (ball.number == 8 || ball instanceof CueBall)
                continue;

            if ((isStriped && ball.striped) || (!isStriped && !ball.striped))
                return true;
        }
        return false;
    }

    private Player getOtherPlayer() {
        for (Player player : getPlayers()) {
            if (this.player != player) {
                return player;
            }
        }

        return null;
    }

    @Override
    protected List<PoolBall> createBalls() {
        List<PoolBall> balls = new ArrayList<>();

        Integer[] numbers = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        Utility.shuffle(numbers);
        Utility.moveNumber(numbers, 8, 4);

        int radius = Config.BALL_RADIUS;
        int diameter = 2 * radius;

        Vector2D position;

        double x = 2 * getBounds().width / 3.0;
        double y = (getBounds().height / 2.0) - 4 * radius;

        int col = 1;
        int row = 0;
        for (int i = 0; i < 15; i++) {
            if (col == row) {
                col++;
                row = 0;
            }

            int number = numbers[i];
            position = new Vector2D(x + col * (diameter - 0.25*radius), y + (5 - col) * radius + row * diameter);
            balls.add(BallFactory.createStandardBall(number, this, position, radius));

            row++;
        }

        // Add cue ball
        Vector2D cueBallPosition = new Vector2D(Config.DEFAULT_X_LINE * getPlayableBounds().getMaxX() + Config.LINE_SIZE, height / 2.0);
        balls.add(new CueBall(this, cueBallPosition, Config.BALL_RADIUS));

        return balls;
    }
}
