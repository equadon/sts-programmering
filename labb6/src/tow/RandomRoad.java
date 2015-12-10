package tow;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

class RandomRoad extends Level {

    RandomRoad() {

        int numberOfObstacles = 8;
        final double minSize = 50 * GameArea.scale;
        final double maxSize = 200 * GameArea.scale;

        Ellipse2D.Double randomShape;


        goals.add(new Goal(950, 950));

        theCar = newCar(100, 300, DOWN);
        theTrailer = newTrailer(DOWN, theCar);

        Area carArea = new Area(new Rectangle2D.Double(0, 0, 200 * GameArea.scale, 400 * GameArea.scale));
        Area goalArea = new Area(new Rectangle2D.Double(800 * GameArea.scale, 800 * GameArea.scale, 200 * GameArea.scale, 200 * GameArea.scale));

        while (numberOfObstacles > 0) {
            randomShape = new Ellipse2D.Double((Math.random() * GameArea.sizeX), (Math.random() * GameArea.sizeY), (Math.random() * (maxSize - minSize)) + minSize, (Math.random() * (maxSize - minSize)) + minSize);
            Area avoid = new Area();
            for (Obstacle otherObstacle : obstacles) {
                avoid.add(otherObstacle.getArea());
            }
            avoid.add(carArea);
            avoid.add(goalArea);
            avoid.intersect(new Area(randomShape));
            if (avoid.isEmpty()) {
                obstacles.add(new Obstacle(randomShape, Color.BLACK));
                numberOfObstacles--;
            }
        }
        name = "Random road";
    }
}
