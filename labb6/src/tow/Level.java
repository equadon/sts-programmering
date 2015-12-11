package tow;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

abstract class Level {
    String name;
    ArrayList<Obstacle> obstacles;
    ArrayList<Goal> goals;
    Car theCar;
    Trailer theTrailer;
    final int maxTime;

    final double borderWidth = 10;

    private final double CAR_BETWEEN_AXES;
    private final double CAR_FRONT;
    private final double CAR_REAR;
    private final double CAR_WIDTH;

    final Direction RIGHT = new Direction(1, 0);
    final Direction DOWN = new Direction(0, 1);
    final Direction LEFT = new Direction(-1, 0);
    final Direction UP = new Direction(0, -1);
    final Direction DUMMY = new Direction(0, 0);

    private final double TRAILER_BETWEEN_AXES;
    private final double TRAILER_FRONT = 0;
    private final double TRAILER_REAR;
    private final double TRAILER_WIDTH;
    private final Color CAR_COLOR = Color.BLUE;
    private final Color TRAILER_COLOR = Color.DARK_GRAY;

    Level() {

        maxTime = 90;

        CAR_BETWEEN_AXES = 100 * GameArea.scale;
        CAR_FRONT = CAR_BETWEEN_AXES / 5;
        CAR_REAR = CAR_BETWEEN_AXES / 4;
        CAR_WIDTH = CAR_BETWEEN_AXES / 3;
        TRAILER_BETWEEN_AXES = 100 * GameArea.scale;
        TRAILER_REAR = TRAILER_BETWEEN_AXES / 2;
        TRAILER_WIDTH = CAR_WIDTH * 1.2;

        obstacles = generateBorders();
        goals = new ArrayList();
    }

    private ArrayList<Obstacle> generateBorders() {

        ArrayList<Obstacle> result = new ArrayList();

        result.add(new RectangleObstacle(0, 0, borderWidth, 1000));
        result.add(new RectangleObstacle(0, 0, 1000, borderWidth));
        result.add(new RectangleObstacle(0, 1000 - borderWidth, 1000, borderWidth));
        result.add(new RectangleObstacle(1000 - borderWidth, 0, borderWidth, 1000));
        return result;
    }

    Car newCar(int xCoord, int yCoord, Direction direction) {

        return new Car(Coord.mul(GameArea.scale, new Coord(xCoord, yCoord)), CAR_BETWEEN_AXES, direction, CAR_FRONT, CAR_REAR, CAR_WIDTH, CAR_COLOR, this);
    }

    Trailer newTrailer(Direction direction, Vehicle towedBy) {

        Trailer trailer = new Trailer(DUMMY, TRAILER_BETWEEN_AXES, direction, TRAILER_FRONT, TRAILER_REAR, TRAILER_WIDTH, TRAILER_COLOR, this);
        towedBy.connectTow(trailer);
        return trailer;
    }

    boolean isOnObstacle(Vehicle vehicle) {

        boolean collision = false;
        for (Obstacle obstacle : obstacles) {
            if (obstacle.isOn(vehicle)) {
                collision = true;
            }
        }
        return collision;
    }

    void checkGoals(Vehicle trailer) {
        for (Goal goal : goals) {
            if (goal.isOn(trailer)) {
                goal.reach();
            }
        }
    }

    boolean allGoalsAreReached() {

        for (Goal goal : goals) {
            if (!goal.isReached()) {
                return false;
            }
        }
        return true;
    }

    void paint(Graphics2D g2) {

        for (Obstacle obstacle : obstacles) {
            obstacle.paint(g2);
        }
        for (Goal goal : goals) {
            goal.paint(g2);
        }
        theCar.paint(g2);
    }
}
