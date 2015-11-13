package labb4.game.objects;

import labb4.game.Config;
import labb4.game.Table;
import labb4.game.Vector2D;
import labb4.game.interfaces.Collidable;
import labb4.game.painters.BallPainter;

import java.awt.*;

public class Ball extends GameObject implements Collidable {
    private final Table table;
    private double radius;

    public Ball(Table table, Vector2D position, Vector2D velocity, Color color, double radius) {
        this(table, position, velocity, color, Config.DEFAULT_MASS, Config.DEFAULT_FRICTION, radius);
    }

    public Ball(Table table, Vector2D position, Vector2D velocity, Color color, double mass, double friction, double radius) {
        super(new BallPainter(), position, velocity, color, mass, friction);

        this.table = table;
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
        bounds.x = position.x - radius;
        bounds.y = position.y - radius;
        bounds.width = getDiameter();
        bounds.height = getDiameter();
    }

    @Override
    public boolean foundCollisions() {
        return collisionWithWalls() || collisionWithBalls();
    }

    @Override
    public void handleCollisions() {

    }

    /**
     * Collision detection.
     */
    private boolean collisionWithWalls() {
        return false;
    }

    private boolean collisionWithBalls() {
        return false;
    }
}
