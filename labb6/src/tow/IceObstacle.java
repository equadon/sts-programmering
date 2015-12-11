package tow;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class IceObstacle extends Obstacle {
    IceObstacle(float x, float y, float width, float height) {
        super(new Area(new Ellipse2D.Float(x, y, width, height)), new Color(10, 186, 191));
    }

    @Override
    boolean isOn(Vehicle vehicle) {
        if (vehicle instanceof Car) {
            if (super.isOn(vehicle)) {
                vehicle.disableSteering();
            } else {
                vehicle.enableSteering();
            }
        }

        return false;
    }
}
