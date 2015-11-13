package labb4.game;

import java.awt.*;

public class Ball extends GameObject implements Collidable {
    private double radius;

    public Ball(Vector2D position, Vector2D velocity, double radius) {
        this(position, velocity, Config.DEFAULT_COLOR, Config.DEFAULT_MASS, Config.DEFAULT_FRICTION, radius);
    }

    public Ball(Vector2D position, Vector2D velocity, Color color, double mass, double friction, double radius) {
        super(position, velocity, color, mass, friction);

        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public double getDiameter() {
        return 2 * radius;
    }

    @Override
    protected void updateBounds() {
        bounds.x = position.x - radius;
        bounds.y = position.y - radius;
        bounds.width = getDiameter();
        bounds.height = getDiameter();
    }

    @Override
    public boolean foundCollisions() {
        return collisionsWithWalls() || collisionWithBalls();
    }

    @Override
    public void handleCollisions() {

    }

    /**
     * Collision detection.
     */
    private boolean collisionsWithWalls() {
        return false;
    }

    private boolean collisionWithBalls() {
        return false;
    }
}
