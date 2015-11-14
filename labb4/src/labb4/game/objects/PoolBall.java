package labb4.game.objects;

import labb4.game.Config;
import labb4.game.Table;
import labb4.game.Vector2D;

import java.awt.*;

public class PoolBall extends Ball {
    public final int points;

    public PoolBall(Table table, Vector2D position, Color color, double radius, boolean striped, int points) {
        this(table, position, Vector2D.zero(), color, Config.DEFAULT_MASS, Config.DEFAULT_FRICTION, radius,
                striped, points);
    }

    public PoolBall(Table table, Vector2D position, Vector2D velocity, Color color, double mass, double friction,
                    double radius, boolean striped, int points) {
        super(table, position, velocity, color, mass, friction, radius, striped);

        this.points = points;
    }

    @Override
    public String toString() {
        return "PoolBall{" +
                "points=" + points +
                '}';
    }
}
