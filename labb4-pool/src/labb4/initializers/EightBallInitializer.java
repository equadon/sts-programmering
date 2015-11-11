package labb4.initializers;

import labb4.Ball;
import labb4.Config;
import labb4.CueBall;
import labb4.painters.BallPainter;

import java.awt.*;

public class EightBallInitializer implements BallsInitializer {
    @Override
    public Ball[] init() {
        return new Ball[] {
                new Ball(100, 100, Config.BALL_RADIUS, Color.BLUE, new BallPainter()),
                new CueBall(150, 600, Config.BALL_RADIUS, new BallPainter())
        };
    }
}
