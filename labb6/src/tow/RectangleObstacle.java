package tow;

import java.awt.*;
import java.awt.geom.Rectangle2D;

class RectangleObstacle extends Obstacle {

    RectangleObstacle(double x, double y, double sx, double sy) {

        super((new Rectangle2D.Double(x * GameArea.scale, y * GameArea.scale, sx * GameArea.scale, sy * GameArea.scale)), Color.BLACK);
    }

    RectangleObstacle(double x, double y, double sx, double sy, Color c) {

        super((new Rectangle2D.Double(x * GameArea.scale, y * GameArea.scale, sx * GameArea.scale, sy * GameArea.scale)), c);
    }
}
