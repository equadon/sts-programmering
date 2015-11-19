package labb4;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * ****************************************************************************************
 * Ball:
 *
 * The ball has instance variables relating to its graphics and game state:
 * position, velocity, and the position from which a shot is aimed (if any).
 *
 */
class Ball {
    public static final double FRICTION = 0.015;
    public static final double RADIUS = 15;

    private final int    BORDER_THICKNESS    = 2;

    private final double FRICTION_PER_UPDATE;

    private Rectangle2D tableBounds;
    Coord position;

    private Coord lastPosition;
    private Coord velocity;
    private Coord aimPosition;              // if aiming for a shot, ow null
    private Rectangle.Double bounds;
    private final double friction;

    public final double radius;
    public final double diameter;

    protected Color color = Color.WHITE;

    private boolean visible;


    Ball(Rectangle tableBounds, Coord initialPosition, double friction) {
        this(tableBounds, initialPosition, new Coord(0, 0), friction, RADIUS);
    }

    Ball(Rectangle tableBounds, Coord initialPosition, Coord velocity, double friction, double radius) {
        this.tableBounds = tableBounds;
        position = initialPosition;
        this.friction = friction;
        lastPosition = initialPosition.clone();
        this.velocity = velocity;

        this.radius = radius;
        diameter = 2 * radius;

        bounds = new Rectangle.Double(position.x - radius, position.y - radius, diameter, diameter);

        FRICTION_PER_UPDATE = 1.0 - Math.pow(1.0 - friction, 100.0 / Pool.UPDATE_FREQUENCY);

        visible = true;
    }

    private boolean isAiming() {
        return aimPosition != null;
    }

    boolean isMoving() {                                // if moving too slow I am deemed to have stopped
        return velocity.magnitude() > FRICTION_PER_UPDATE;
    }

    public boolean isVisible() {
        return visible;
    }

    public void show() {
        visible = true;
    }

    public void hide() {
        visible = false;
    }

    private void updateBounds() {
        bounds.x = position.x - radius;
        bounds.y = position.y - radius;
    }

    public Rectangle.Double getBounds() {
        return bounds;
    }

    void shoot() {
        if (isAiming()) {
            Coord aimingVector = Coord.sub(position, aimPosition);
            velocity = Coord.mul(Math.sqrt(10.0 * aimingVector.magnitude() / Pool.UPDATE_FREQUENCY),
                    aimingVector.norm());  // don't ask - determined by experimentation
            aimPosition = null;
        }
    }

    void setAimPosition(Coord grabPosition) {
        if (Coord.distance(position, grabPosition) <= radius) {
            aimPosition = grabPosition;
        }
    }

    void updateAimPosition(Coord newPosition) {
        if (isAiming()){
            aimPosition = newPosition;
        }
    }

    void move() {
        if (isMoving()) {
            lastPosition.x = position.x;
            lastPosition.y = position.y;

            position.increase(velocity);

            velocity.decrease(Coord.mul(FRICTION_PER_UPDATE, velocity.norm()));

            updateBounds();
        }
    }

    public boolean checkCollisions(Ball[] balls) {
        return ballsCollided(balls) || checkWallCollision();
    }

    private boolean checkWallCollision() {
        boolean collision = false;

        if (bounds.x < tableBounds.getX()) { // Left wall
            position.x += tableBounds.getX() - bounds.x;
            velocity.x = -velocity.x;
            collision = true;
        } else if (bounds.getMaxX() > tableBounds.getMaxX()) { // right wall
            position.x -= bounds.getMaxX() - tableBounds.getMaxX();
            velocity.x = -velocity.x;
            collision = true;
        }

        if (bounds.y < tableBounds.getY()) { // top wall
            position.y += tableBounds.getY() - bounds.y;
            velocity.y = -velocity.y;
            collision = true;
        } else if (bounds.getMaxY() > tableBounds.getMaxY()) { // bottom wall
            position.y -= bounds.getMaxY() - tableBounds.getMaxY();
            velocity.y = -velocity.y;
            collision = true;
        }

        if (collision) {
            updateBounds();
        }

        return collision;
    }

    private boolean ballsCollided(Ball[] balls) {
        boolean foundCollision = false;

        for (Ball ball : balls) {
            if (ball != this) {
                if (ball.isVisible() && checkBallCollision(ball)) {
                    foundCollision = true;
                }
            }
        }

        return foundCollision;
    }

    private boolean checkBallCollision(Ball ballB) {
        double prevDistance = Coord.distance(lastPosition, ballB.lastPosition);
        double distance = Coord.distance(position, ballB.position);

        if (prevDistance > distance && distance < radius + ballB.radius) {
            handleBallCollision(ballB);

            collidedWith(ballB);
            return true;
        }

        return false;
    }

    protected void collidedWith(Ball ballB) {}

    private void handleBallCollision(Ball ballB) {
        Coord impulseDirection = calcImpulseDirection(ballB);
        Coord impulseVector = calcImpulseVector(ballB, impulseDirection);

        Coord diff = Coord.sub(position, ballB.position);

        double moveDistance = diff.magnitude();
        Coord moveVector = Coord.mul((radius + ballB.radius - moveDistance) / 2.0, diff.norm()); // divide by two to distribute movement evenly among the two balls

        // Adjust ball position
        position.increase(moveVector);
        ballB.position.decrease(moveVector);

        updateBounds();

        velocity.increase(impulseVector);
        ballB.velocity.decrease(impulseVector);
    }

    private Coord calcImpulseDirection(Ball ballB) {
        return new Coord(
                (position.x - ballB.position.x) / Coord.distance(position, ballB.position), // Dx
                (position.y - ballB.position.y) / Coord.distance(position, ballB.position)  // Dy
        );
    }

    private Coord calcImpulseVector(Ball ballB, Coord impulseDirection) {
        double impulse = Coord.scal(ballB.velocity, impulseDirection) - Coord.scal(velocity, impulseDirection);

        return Coord.mul(impulse, impulseDirection);
    }

    // paint: to draw the ball, first draw a black ball
    // and then a smaller ball of proper color inside
    // this gives a nice thick border
    void paint(Graphics2D g2D) {
        g2D.setColor(Color.black);
        g2D.fillOval(
                (int) (position.x - radius + 0.5),
                (int) (position.y - radius + 0.5),
                (int) diameter,
                (int) diameter);
        g2D.setColor(color);
        g2D.fillOval(
                (int) (position.x - radius + 0.5 + BORDER_THICKNESS),
                (int) (position.y - radius + 0.5 + BORDER_THICKNESS),
                (int) (diameter - 2 * BORDER_THICKNESS),
                (int) (diameter - 2 * BORDER_THICKNESS));
        if (isAiming()) {
            paintAimingLine(g2D);
        }
    }

    private void paintAimingLine(Graphics2D graph2D) {
        Coord.paintLine(
                graph2D,
                aimPosition,
                Coord.sub(Coord.mul(2, position), aimPosition)
        );
    }
} // end  class Ball

