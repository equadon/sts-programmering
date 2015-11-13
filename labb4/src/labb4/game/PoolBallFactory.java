package labb4.game;

import labb4.game.objects.PoolBall;

import java.awt.*;

public class PoolBallFactory {
    public static PoolBall createBall(int number, Table table, Vector2D position, int radius) {
        boolean striped = number > 8;
        Color color;

        if (number == 1 || number == 9)
            color = Config.BALL_YELLOW_COLOR;
        else if (number == 2 || number == 10)
            color = Config.BALL_BLUE_COLOR;
        else if (number == 3 || number == 11)
            color = Config.BALL_RED_COLOR;
        else if (number == 4 || number == 12)
            color = Config.BALL_PURPLE_COLOR;
        else if (number == 5 || number == 13)
            color = Config.BALL_ORANGE_COLOR;
        else if (number == 6 || number == 14)
            color = Config.BALL_GREEN_COLOR;
        else if (number == 7 || number == 15)
            color = Config.BALL_BROWN_COLOR;
        else if (number == 8)
            color = Config.BALL_BLACK_COLOR;
        else
            color = Config.BALL_WHITE_COLOR;

        return new PoolBall(table, position, color, radius, striped, number);
    }
}
