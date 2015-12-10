package tow;

import java.awt.*;
import java.awt.geom.Area;

class Obstacle {

    private final Shape shape;
    private final Area area;
    private final Color color;

    Obstacle(Shape s, Color c) {

        shape = s;
        color = c;
        area = new Area(shape);
    }

    void paint(Graphics2D g, Color otherColor) {

        g.setColor(otherColor);
        g.fill(shape);
    }

    void paint(Graphics2D g) {

        g.setColor(color);
        g.fill(shape);
    }

    boolean isOn(Vehicle vehicle) {

        Area vehicleArea = new Area(vehicle.appearance);
        vehicleArea.intersect(area);
        return !vehicleArea.isEmpty();
    }

    Area getArea() {
        return area;
    }
}
