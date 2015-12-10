package tow;

import java.awt.*;

class Goal extends RectangleObstacle {

    private boolean isReached;
    private static final Color goalColor = Color.RED;
    private static final Color reachedColor = Color.GREEN;
    private final static double GOAL_SIZE = 25;

    Goal(double x, double y) {

        super(x, y, GOAL_SIZE, GOAL_SIZE, goalColor);
    }

    boolean isReached() {
        return isReached;
    }

    void reach() {
        isReached = true;
    }

    @Override
    void paint(Graphics2D g) {

        if (isReached) {
            super.paint(g, reachedColor);
        } else {
            super.paint(g);
        }
    }
}
