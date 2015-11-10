package sts.labb4;

import java.awt.*;

/**
 * ****************************************************************************************
 * Ball:
 *
 * The ball has instance variables relating to its graphics and game state:
 * position, velocity, and the position from which a shot is aimed (if any).
 *
 */
class Ball {

    private final Color  COLOR               = Color.white;
    private final int    BORDER_THICKNESS    = 2;
    private final double RADIUS              = 15;
    private final double DIAMETER            = 2 * RADIUS;
    private final double FRICTION            = 0.015;   // its friction constant (normed for 100 updates/second)
    private final double FRICTION_PER_UPDATE =          // friction applied each simulation step
            1.0 - Math.pow(1.0 - FRICTION,       // don't ask - I no longer remember how I got to this
                    100.0 / Labb4.UPDATE_FREQUENCY);
    private Coord position;
    private Coord velocity;
    private Coord aimPosition;              // if aiming for a shot, ow null

    Ball(Coord initialPosition) {
        position = initialPosition;
        velocity = Coord.ZERO;       // WARNING! Are initial velocities clones or aliases??
    }

    private boolean isAiming() {
        return aimPosition != null;
    }

    boolean isMoving() {                                // if moving too slow I am deemed to have stopped
        return velocity.magnitude() > FRICTION_PER_UPDATE;
    }

    void shoot() {
        if (isAiming()) {
            Coord aimingVector = Coord.sub(position, aimPosition);
            velocity = Coord.mul(Math.sqrt(10.0 * aimingVector.magnitude() / Labb4.UPDATE_FREQUENCY),
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
            position.increase(velocity);
            velocity.decrease(Coord.mul(FRICTION_PER_UPDATE, velocity.norm()));
        }
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