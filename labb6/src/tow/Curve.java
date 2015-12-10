package tow;

import java.awt.*;
import java.awt.geom.Ellipse2D;

class Curve extends Level {

    Curve() {

        goals.add(new Goal(350, 100));

        Ellipse2D.Double pond = new Ellipse2D.Double(200 * GameArea.scale, 200 * GameArea.scale, 500 * GameArea.scale, GameArea.sizeY - 400 * GameArea.scale);
        obstacles.add(new Obstacle(pond, Color.CYAN));
        obstacles.add(new RectangleObstacle(450, 0, borderWidth, 200));

        theCar = newCar(900, 300, UP);
        theTrailer = newTrailer(Direction.rotate(UP, 5), theCar);
        name = "Run around";
    }
}
