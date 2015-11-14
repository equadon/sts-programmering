package labb4.game.objects;

import labb4.game.Config;
import labb4.game.Vector2D;
import labb4.game.interfaces.Movable;
import labb4.game.ui.painters.ObjectPainter;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class GameObject implements Movable {
    private boolean visible;

    protected final Rectangle.Double bounds;
    private final ObjectPainter painter;

    protected Vector2D lastPosition;
    protected Vector2D position;
    protected Vector2D velocity;

    protected Color color;
    protected double mass;
    protected double friction;

    private double frictionPerUpdate;

    public GameObject(ObjectPainter painter, Vector2D position, Vector2D velocity, Color color, double mass, double friction) {
        this.painter = painter;
        this.position = position;
        this.velocity = velocity;
        this.color = color;
        this.mass = mass;
        this.friction = friction;

        frictionPerUpdate = 1.0 - Math.pow(1.0 - friction, 100.0 / Config.FRAMES_PER_SECOND);

        lastPosition = position;
        bounds = new Rectangle2D.Double(position.x, position.y, 0, 0);
        visible = true;
    }

    public Rectangle.Double getBounds() {
        return bounds;
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public Color getColor() {
        return color;
    }

    public double getMass() {
        return mass;
    }

    public double getFriction() {
        return friction;
    }

    public Vector2D getLastPosition() {
        return lastPosition;
    }

    protected abstract void updateBounds();

    public boolean isVisible() {
        return visible;
    }

    public boolean isMoving() {
        return velocity.length() > frictionPerUpdate;
    }

    public void update() {
        move();
    }

    public void draw(Graphics2D g) {
        painter.draw(g, this);
    }

    public void move() {
        if (isMoving()) {
            lastPosition.x = position.x;
            lastPosition.y = position.y;

            Vector2D frictionVector = velocity.clone().normalize().multiply(frictionPerUpdate);

            position.add(velocity);
            velocity.subtract(frictionVector);

            updateBounds();
        }
    }

    public void hide() {
        visible = false;
    }

    public void show() {
        visible = true;
    }

    public void toggle() {
        visible = !visible;
    }
}
