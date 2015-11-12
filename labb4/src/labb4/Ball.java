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

    private final Color COLOR               = Color.white;
    private final int    BORDER_THICKNESS    = 2;
    private final double RADIUS              = 15;
    private final double DIAMETER            = 2 * RADIUS;
    private final double FRICTION            = 0.015;   // its friction constant (normed for 100 updates/second)
    private final double FRICTION_PER_UPDATE =          // friction applied each simulation step
            1.0 - Math.pow(1.0 - FRICTION,       // don't ask - I no longer remember how I got to this
                    100.0 / Pool.UPDATE_FREQUENCY);
    private final Table table;
    private Coord position;
    private Coord lastPosition;
    private Coord velocity;
    private Coord aimPosition;              // if aiming for a shot, ow null
    private Rectangle.Double bounds;

    Ball(Table table, Coord initialPosition) {
        this.table = table;
        position = initialPosition;
        lastPosition = initialPosition.clone();
        velocity = new Coord(0, 0);

        bounds = new Rectangle.Double(position.x - RADIUS, position.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
    }

    private boolean isAiming() {
        return aimPosition != null;
    }

    boolean isMoving() {                                // if moving too slow I am deemed to have stopped
        return velocity.magnitude() > FRICTION_PER_UPDATE;
    }

    private void updateBounds() {
        bounds.x = position.x - RADIUS;
        bounds.y = position.y - RADIUS;
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
        if (Coord.distance(position, grabPosition) <= RADIUS) {
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
            nextPosition();

            if (!foundBallCollision() && !foundWallCollision()) {
                velocity.decrease(Coord.mul(FRICTION_PER_UPDATE, velocity.norm()));
            }
        }
    }

    private void nextPosition() {
        lastPosition.x = position.x;
        lastPosition.y = position.y;

        position.increase(velocity);

        updateBounds();
    }

    private boolean foundWallCollision() {
        boolean collision = false;

        // Right/left wall
        if (bounds.getMaxX() > table.innerBounds.getMaxX() ||
                bounds.x < table.innerBounds.x) {
            velocity.x = -velocity.x;
            collision = true;
        }

        // Bottom/top wall
        if (bounds.getMaxY() > table.innerBounds.getMaxY() ||
                bounds.y < table.innerBounds.y) {
            velocity.y = -velocity.y;
            collision = true;
        }

        return collision;
    }

    private boolean foundBallCollision() {
        boolean foundCollision = false;

        for (Ball ball : table.balls) {
            if (ball != this) {
                if (collidedWith(ball)) {
                    foundCollision = true;
                }
            }
        }

        return foundCollision;
    }

    private boolean collidedWith(Ball ballB) {
        double prevDistance = Coord.distance(lastPosition, ballB.lastPosition);
        double distance = Coord.distance(position, ballB.position);

        if (prevDistance > distance && distance < 2 * RADIUS) {
            Coord impulseDirection = calcImpulseDirection(ballB);
            Coord impulseVector = calcImpulseVector(ballB, impulseDirection);

            Coord delta = Coord.sub(position, ballB.position);
            Coord mtd = Coord.mul(0.5 * (RADIUS + RADIUS - delta.magnitude()) / delta.magnitude(), delta);

            position.increase(mtd);
            ballB.position.decrease(mtd);

            velocity.increase(impulseVector);
            ballB.velocity.decrease(impulseVector);

            return true;
        } else {
            return false;
        }
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
                (int) (position.x - RADIUS + 0.5),
                (int) (position.y - RADIUS + 0.5),
                (int) DIAMETER,
                (int) DIAMETER);
        g2D.setColor(COLOR);
        g2D.fillOval(
                (int) (position.x - RADIUS + 0.5 + BORDER_THICKNESS),
                (int) (position.y - RADIUS + 0.5 + BORDER_THICKNESS),
                (int) (DIAMETER - 2 * BORDER_THICKNESS),
                (int) (DIAMETER - 2 * BORDER_THICKNESS));
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

