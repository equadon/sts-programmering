package tow;

import java.awt.*;
import java.awt.geom.Area;

abstract class Vehicle {

    class Collided extends Exception {
    }

    ;
    final Collided collisionHappened = new Collided();

    private final Level level;

    Coord frontAxisPos;      //midpoint beetween front wheels
    Coord rearAxisPos;       // midpoint between rear wheels
    Direction direction;     // unit vector vehicle orientation

    Coord rearPos;           // rearmost point
    Coord frontPos;          // foremost point

    Coord saveFrontAxisPos;   // to save the state
    Direction saveDirection;

    private final double lengthBetweenAxes; // distance bewtween front and rear axes
    private final double frontLength;  // distance between front axis and front of vehicle
    private final double rearLength;   // distance between rear axis and rear of vehicle
    final double width;        // width of vehicle
    private final double HOOK_SIZE;  // size of rear hook
    final double wheelWidth;
    final double halfWheelLength;

    private final Color color;
    private final Color crashColor = Color.RED;
    private boolean justHasCrashed = false;
    final Color wheelColor = Color.LIGHT_GRAY;

    private Trailer towed;

    Shape appearance;

    Vehicle(Coord frontAxisPos, double lengthBetweenAxes, Direction direction, double frontLength, double rearLength, double width, Color color, Level lev) {

        this.frontAxisPos = frontAxisPos;
        this.lengthBetweenAxes = lengthBetweenAxes;
        this.frontLength = frontLength;
        this.rearLength = rearLength;
        this.width = width;
        this.color = color;
        this.direction = direction;
        this.level = lev;

        HOOK_SIZE = 5 * GameArea.scale;
        wheelWidth = 5 * GameArea.scale;
        halfWheelLength = 10 * GameArea.scale;

        setPositions();
    }

    final void setPositions() {

        frontPos = Coord.add(frontAxisPos, Coord.mul(frontLength, direction));
        rearAxisPos = Coord.sub(frontAxisPos, Coord.mul(lengthBetweenAxes, direction));
        rearPos = Coord.sub(rearAxisPos, Coord.mul(rearLength, direction));
        if (isTowing()) {
            towed.frontAxisPos = rearPos;
        }
        appearance = appearance();
    }

    void connectTow(Trailer tow) {

        towed = tow;
        towed.frontAxisPos = this.rearPos;
        towed.setPositions();
    }

    private boolean isTowing() {
        return towed != null;
    }

    void move() throws Collided {        // assumes frontAxisPos has been changed

        direction = Coord.sub(frontAxisPos, rearAxisPos).norm(); // Good enough approximation!
        setPositions();

        if (level.isOnObstacle(this)) {
            crash();
            throw collisionHappened;
        }

        if (isTowing()) {
            towed.move();
            if (areDangerousAngles() && hasCollidedWithTrailer()) {
                towed.crash();
                this.crash();
                throw collisionHappened;
            }
        }
    }

    void crash() {

        justHasCrashed = true;
    }

    void saveState() {

        justHasCrashed = false;
        saveDirection = direction;
        if (isTowing()) {
            towed.saveState();
        }
    }

    void restoreState() {

        direction = saveDirection;
        setPositions();
        if (isTowing()) {
            towed.restoreState();
        }
    }

    private boolean areDangerousAngles() {

        double theDangerousAngle = 60;
        double newAngle = Direction.angle(direction, towed.direction);
        if (newAngle < theDangerousAngle) {
            return false;
        }
        double oldAngle = Direction.angle(saveDirection, towed.saveDirection);
        if (oldAngle > newAngle) {
            return false;
        } else {
            return true;
        }
    }

    private boolean hasCollidedWithTrailer() {

        Area myArea = new Area(appearance);
        Area towArea = new Area(towed.appearance);
        myArea.intersect(towArea);
        return !myArea.isEmpty();
    }

    abstract Shape appearance();

    void paint(Graphics2D g2) {

        g2.setColor(color);
        if (justHasCrashed) {
            g2.setColor(crashColor);
        }
        g2.fill(appearance());

        if (isTowing()) {
            g2.fillOval((int) (rearPos.x - HOOK_SIZE), (int) (rearPos.y - HOOK_SIZE), (int) (HOOK_SIZE * 2), (int) (HOOK_SIZE * 2));
            towed.paint(g2);
        }

        paintRearWheels(g2);
    }

    Coord wheeltip(Coord center, double length, Direction direction) {

        return Coord.add(center, Coord.mul(length, direction));
    }

    private void paintRearWheels(Graphics2D g) {

        final double wheelDisplacement = width - wheelWidth;
        Coord leftWheelPos = Coord.add(rearAxisPos, Coord.mul(wheelDisplacement, Direction.rotate(direction, 90)));
        Coord rightWheelPos = Coord.sub(rearAxisPos, Coord.mul(wheelDisplacement, Direction.rotate(direction, 90)));
        g.setColor(wheelColor);
        g.setStroke(new BasicStroke((int) wheelWidth));
        g.drawLine((int) wheeltip(leftWheelPos, halfWheelLength, direction).x, (int) wheeltip(leftWheelPos, halfWheelLength, direction).y, (int) wheeltip(leftWheelPos, -halfWheelLength, direction).x, (int) wheeltip(leftWheelPos, -halfWheelLength, direction).y);
        g.drawLine((int) wheeltip(rightWheelPos, halfWheelLength, direction).x, (int) wheeltip(rightWheelPos, halfWheelLength, direction).y, (int) wheeltip(rightWheelPos, -halfWheelLength, direction).x, (int) wheeltip(rightWheelPos, -halfWheelLength, direction).y);
    }
}
