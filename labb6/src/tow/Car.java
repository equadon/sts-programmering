package tow;

import java.awt.*;

class Car extends Vehicle {

    private Direction frontWheelDirection;      // unit vector where front wheels are pointed

    Car(Coord frontAxisPos, double lengthBetweenAxes, Direction direction, double frontLength, double rearLength, double width, Color color, Level lev) {

        super(frontAxisPos, lengthBetweenAxes, direction, frontLength, rearLength, width, color, lev);

        frontWheelDirection = direction;
    }

    boolean moveSuccessfully(double velocity, int frontWheelAngle) {

        try {
            saveState();
            frontWheelDirection = Direction.rotate(direction, frontWheelAngle);
            Coord frontAxisMove = Coord.mul(velocity, frontWheelDirection);
            frontAxisPos = Coord.add(frontAxisPos, frontAxisMove);
            move();
            return true;
        } catch (Collided c) {
            restoreState();
            return false;
        }
    }

    @Override
    void saveState() {

        saveFrontAxisPos = frontAxisPos;
        super.saveState();
    }

    @Override
    void restoreState() {

        frontAxisPos = saveFrontAxisPos;
        super.restoreState();
    }

    @Override
    Polygon appearance() {

        Coord widthOffset = Coord.mul(width, Direction.rotate(direction, 90));
        Coord frontLeft = Coord.add(frontPos, widthOffset);
        Coord frontRight = Coord.sub(frontPos, widthOffset);
        Coord rearLeft = Coord.add(rearPos, widthOffset);
        Coord rearRight = Coord.sub(rearPos, widthOffset);
        int[] xpoints = {(int) frontLeft.x, (int) rearLeft.x, (int) rearRight.x, (int) frontRight.x};
        int[] ypoints = {(int) frontLeft.y, (int) rearLeft.y, (int) rearRight.y, (int) frontRight.y};
        return new Polygon(xpoints, ypoints, 4);
    }

    @Override
    void paint(Graphics2D g2) {

        super.paint(g2);
        paintFrontWheels(g2);
    }

    private void paintFrontWheels(Graphics2D g) {

        final double wheelDisplacement = width - wheelWidth;
        Coord leftWheelPos = Coord.add(frontAxisPos, Coord.mul(wheelDisplacement, Direction.rotate(direction, 90)));
        Coord rightWheelPos = Coord.sub(frontAxisPos, Coord.mul(wheelDisplacement, Direction.rotate(direction, 90)));

        g.setColor(wheelColor);
        g.setStroke(new BasicStroke((int) wheelWidth));
        g.drawLine((int) wheeltip(leftWheelPos, halfWheelLength, frontWheelDirection).x, (int) wheeltip(leftWheelPos, halfWheelLength, frontWheelDirection).y, (int) wheeltip(leftWheelPos, -halfWheelLength, frontWheelDirection).x, (int) wheeltip(leftWheelPos, -halfWheelLength, frontWheelDirection).y);
        g.drawLine((int) wheeltip(rightWheelPos, halfWheelLength, frontWheelDirection).x, (int) wheeltip(rightWheelPos, halfWheelLength, frontWheelDirection).y, (int) wheeltip(rightWheelPos, -halfWheelLength, frontWheelDirection).x, (int) wheeltip(rightWheelPos, -halfWheelLength, frontWheelDirection).y);
    }
}
