package labb4.game.painters;

import labb4.game.Ball;
import labb4.game.GameObject;

import java.awt.*;

public class BallPainter extends ObjectPainter {
    @Override
    public void draw(Graphics2D g, GameObject object) {
        Ball ball = (Ball) object;
    }
}
