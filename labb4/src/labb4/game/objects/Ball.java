package labb4.game.objects;

import labb4.game.Config;
import labb4.game.Table;
import labb4.game.Vector2D;
import labb4.game.interfaces.Collidable;
import labb4.game.ui.painters.BallPainter;

import java.awt.*;

public class Ball extends GameObject implements Collidable {
    public final boolean striped;

    protected final Table table;
    private double radius;

    public Ball(Table table, Vector2D position, Vector2D velocity, Color color, double radius, boolean striped) {
        this(table, position, velocity, color, Config.DEFAULT_MASS, Config.DEFAULT_FRICTION, radius, striped);
    }

    public Ball(Table table, Vector2D position, Vector2D velocity, Color color, double mass, double friction,
                double radius, boolean striped) {
        super(new BallPainter(), position, velocity, color, mass, friction);

        this.table = table;
        this.striped = striped;
        this.radius = radius;

        updateBounds();
    }

    public double getRadius() {
        return radius;
    }

    public double getDiameter() {
        return 2 * radius;
    }

    @Override
    protected void updateBounds() {
        bounds.x = getPosition().x - radius;
        bounds.y = getPosition().y - radius;
        bounds.width = getDiameter();
        bounds.height = getDiameter();
    }

    @Override
    public boolean handleCollisions() {
        return isVisible() && (collisionWithWalls() || collisionWithBalls() || collisionWithHoles());
    }

    private boolean collisionWithHoles() {
        for (Hole hole : table.getHoles()) {
            if (hole.handleBallCollision(this)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Collision detection.
     */
    private boolean collisionWithWalls() {
        Rectangle tableBounds = table.getPlayableBounds();
        boolean collision = false;

        if (bounds.x < tableBounds.getX()) { // Left wall
            getPosition().x += tableBounds.getX() - bounds.x;
            velocity.x = -velocity.x;
            collision = true;
        } else if (bounds.getMaxX() > tableBounds.getMaxX()) { // right wall
            getPosition().x -= bounds.getMaxX() - tableBounds.getMaxX();
            velocity.x = -velocity.x;
            collision = true;
        }

        if (bounds.y < tableBounds.getY()) { // top wall
            getPosition().y += tableBounds.getY() - bounds.y;
            velocity.y = -velocity.y;
            collision = true;
        } else if (bounds.getMaxY() > tableBounds.getMaxY()) { // bottom wall
            getPosition().y -= bounds.getMaxY() - tableBounds.getMaxY();
            velocity.y = -velocity.y;
            collision = true;
        }

        if (collision) {
            updateBounds();
        }

        return collision;
    }

    private boolean collisionWithBalls() {
        boolean foundCollision = false;

        for (Ball ball : table.getBalls()) {
            if (ball != this) {
                if (ball.isVisible() && checkCollisionWith(ball)) {
                    foundCollision = true;
                }
            }
        }

        return foundCollision;
    }

    private boolean checkCollisionWith(Ball other) {
        if (bounds.intersects(other.bounds)) {
            double distance = getPosition().distanceTo(other.getPosition());

            if (distance < radius + other.radius) {
                handleBallCollision(other);

                collidedWith(other);
                return true;
            }
        }

        return false;
    }

    protected void collidedWith(Ball other) {}

    private void handleBallCollision(Ball other) {
        Vector2D impulseDirection = calcImpulseDirection(other);
        Vector2D impulseVector = calcImpulseVector(other, impulseDirection);

        Vector2D diff = getPosition().clone().subtract(other.getPosition());

        double moveDistance = diff.length();
        Vector2D moveVector = diff.normalize().multiply((radius + other.radius - moveDistance) / 2.0);

        // Adjust ball position
        getPosition().add(moveVector);
        other.getPosition().subtract(moveVector);

        updateBounds();

        velocity.add(impulseVector);
        other.velocity.subtract(impulseVector);
    }

    private Vector2D calcImpulseDirection(Ball ballB) {
        return new Vector2D(
                (getPosition().x - ballB.getPosition().x) / getPosition().distanceTo(ballB.getPosition()), // Dx
                (getPosition().y - ballB.getPosition().y) / getPosition().distanceTo(ballB.getPosition())  // Dy
        );
    }

    private Vector2D calcImpulseVector(Ball ballB, Vector2D impulseDirection) {
        double impulse = ballB.velocity.dot(impulseDirection) - velocity.dot(impulseDirection);

        return impulseDirection.multiply(impulse);
    }
}
