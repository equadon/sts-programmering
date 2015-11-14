package labb4.game.objects;

import labb4.game.Config;
import labb4.game.Table;
import labb4.game.Vector2D;
import labb4.game.interfaces.Collidable;
import labb4.game.ui.painters.HolePainter;

import java.util.ArrayList;
import java.util.List;

public class Hole extends GameObject implements Collidable {
    private final Table table;
    private final double radius;

    private final List<PoolBall> balls;

    public Hole(Table table, Vector2D position, double radius) {
        super(new HolePainter(), position, new Vector2D(0, 0), Config.DEFAULT_HOLE_COLOR, 0, 0);

        this.table = table;
        this.radius = radius;

        balls = new ArrayList<>();

        updateBounds();
    }

    public PoolBall[] getBalls() {
        return balls.toArray(new PoolBall[balls.size()]);
    }

    @Override
    public boolean handleCollisions() {
        return false;
    }

    public boolean handleBallCollision(Ball ball) {
        if (position.distanceTo(ball.position) < radius) {
            PoolBall poolBall = (PoolBall) ball;

            balls.add(poolBall);
            ball.hide();

            if (ball instanceof CueBall) {
                table.addPoints(-500);
            } else {
                table.addPoints(poolBall.points);
            }

            return true;
        }

        return false;
    }

    @Override
    protected void updateBounds() {
        bounds.x = position.x - radius;
        bounds.y = position.y - radius;
        bounds.width = 2 * radius;
        bounds.height = 2 * radius;
    }
}
