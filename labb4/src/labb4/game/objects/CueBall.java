package labb4.game.objects;

import labb4.game.Config;
import labb4.game.Table;
import labb4.game.Vector2D;
import labb4.game.interfaces.Aimable;

import java.awt.*;
import java.util.logging.Logger;

public class CueBall extends PoolBall implements Aimable {
    private static final Logger LOG = Logger.getLogger(CueBall.class.getName());

    private Vector2D aimPosition;

    public CueBall(Table table, Vector2D position, Vector2D velocity, double radius) {
        this(table, position, velocity, Color.WHITE, Config.DEFAULT_MASS, Config.DEFAULT_FRICTION, radius);
    }

    public CueBall(Table table, Vector2D position, Vector2D velocity, Color color, double mass, double friction,
                   double radius) {
        super(table, position, velocity, color, mass, friction, radius, false, -1);

        aimPosition = null;
    }

    public boolean isAiming() {
        return aimPosition != null;
    }

    @Override
    public Vector2D getAimPosition() {
        return aimPosition;
    }

    @Override
    public void shoot() {
        if (isAiming()) {
            Vector2D aimingVector = aimPosition.subtract(position);
            double length = aimingVector.length();

            velocity = aimingVector.normalize().multiply(-Math.sqrt(20.0 * length / Config.FRAMES_PER_SECOND));

            aimPosition = null;
        }
    }

    @Override
    public void setAim(Vector2D startPosition) {
        if (position.distanceTo(startPosition) < getRadius()) {
            aimPosition = startPosition;
        }
    }

    @Override
    public void updateAim(Vector2D newPosition) {
        if (isAiming()) {
            aimPosition = newPosition;
        }
    }

    @Override
    public String toString() {
        return String.format("CueBall[pos=(%.0f,%.0f), velocity=(%.0f,%.0f), isAiming=%s]",
                position.x, position.y,
                velocity.x, velocity.y,
                isAiming());
    }
}
