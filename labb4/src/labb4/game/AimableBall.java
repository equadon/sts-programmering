package labb4.game;

import labb4.game.interfaces.Aimable;

import java.awt.*;

public class AimableBall extends Ball implements Aimable {
    private Vector2D aimPosition;

    public AimableBall(Table table, Vector2D position, Vector2D velocity, double radius) {
        this(table, position, velocity, Config.DEFAULT_COLOR, Config.DEFAULT_MASS, Config.DEFAULT_FRICTION, radius);
    }

    public AimableBall(Table table, Vector2D position, Vector2D velocity, Color color, double mass, double friction, double radius) {
        super(table, position, velocity, color, mass, friction, radius);

        aimPosition = null;
    }

    public boolean isAiming() {
        return aimPosition != null;
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
