package labb4.game.objects;

import labb4.game.Config;
import labb4.game.Table;
import labb4.game.Vector2D;
import labb4.game.interfaces.Aimable;

import java.awt.*;

public class CueBall extends Ball implements Aimable {
    private Vector2D aimPosition;

    public CueBall(Table table, Vector2D position, Vector2D velocity, double radius) {
        this(table, position, velocity, Color.WHITE, Config.DEFAULT_MASS, Config.DEFAULT_FRICTION, radius);
    }

    public CueBall(Table table, Vector2D position, Vector2D velocity, Color color, double mass, double friction, double radius) {
        super(table, position, velocity, color, mass, friction, radius);

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

    }

    @Override
    public void setAim(Vector2D startPosition) {
        if (position.distanceTo(aimPosition) < getRadius()) {
            aimPosition = startPosition;
        }
    }

    @Override
    public void updateAim(Vector2D newPosition) {
        if (isAiming()) {
            aimPosition = newPosition;
        }
    }
}
