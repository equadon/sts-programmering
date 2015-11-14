package labb4.game;

import labb4.game.objects.PoolBall;

import java.awt.*;

public class PoolBallFactory {
    public static PoolBall createStandardBall(int points, Table table, Vector2D position, int radius) {
        boolean striped = points > 8;
        Color color = Config.BALL_WHITE_COLOR;

        if (points == 1 || points == 9)
            color = Config.BALL_YELLOW_COLOR;
        else if (points == 2 || points == 10)
            color = Config.BALL_BLUE_COLOR;
        else if (points == 3 || points == 11)
            color = Config.BALL_RED_COLOR;
        else if (points == 4 || points == 12)
            color = Config.BALL_PURPLE_COLOR;
        else if (points == 5 || points == 13)
            color = Config.BALL_ORANGE_COLOR;
        else if (points == 6 || points == 14)
            color = Config.BALL_GREEN_COLOR;
        else if (points == 7 || points == 15)
            color = Config.BALL_BROWN_COLOR;
        else if (points == 8)
            color = Config.BALL_BLACK_COLOR;

        return new PoolBall(table, position, color, radius, striped, points);
    }

    public static PoolBall createSnookerBall(int points, Table table, Vector2D position, int radius) {
        Color color = Config.BALL_WHITE_COLOR;

        if (points == 1)
            color = Config.BALL_RED_COLOR;
        else if (points == 2)
            color = Config.BALL_YELLOW_COLOR;
        else if (points == 3)
            color = Config.BALL_GREEN_COLOR;
        else if (points == 4)
            color = Config.BALL_BROWN_COLOR;
        else if (points == 5)
            color = Config.BALL_BLUE_COLOR;
        else if (points == 6)
            color = Config.BALL_PINK_COLOR;
        else if (points == 7)
            color = Config.BALL_BLACK_COLOR;

        return new PoolBall(table, position, color, radius, false, points);
    }
}
