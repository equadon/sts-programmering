package labb4.game.objects;

import labb4.game.Config;
import labb4.game.tables.Table;
import labb4.game.Vector2D;
import labb4.game.ui.painters.PocketPainter;

import java.util.ArrayList;
import java.util.List;

public class Pocket extends Ball {
    private final List<PoolBall> balls;

    public Pocket(Table table, Vector2D position, double radius) {
        super(table, new PocketPainter(), position, Config.DEFAULT_POCKET_COLOR, 0, 0, radius, false);

        balls = new ArrayList<>();

        updateBounds();
    }

    public PoolBall[] getBalls() {
        return balls.toArray(new PoolBall[balls.size()]);
    }

    public void remove(PoolBall ball) {
        balls.remove(ball);
        table.add(ball);
    }

    public void empty() {
        balls.clear();
    }

    public boolean handleBallCollision(Ball ball) {
        if (getPosition().distanceTo(ball.getPosition()) < getRadius()) {
            PoolBall poolBall = (PoolBall) ball;

            add(poolBall);

            return true;
        }

        return false;
    }

    public void add(PoolBall ball) {
        balls.add(ball);

        table.remove(ball);
        ball.hide();

        table.pocketed(ball, this);
    }

    @Override
    protected void updateBounds() {
        bounds.x = getPosition().x - getRadius();
        bounds.y = getPosition().y - getRadius();
        bounds.width = 2 * getRadius();
        bounds.height = 2 * getRadius();
    }

    @Override
    public String toString() {
        return String.format("Pocket[balls=%d, pos=(%.0f,%.0f)]", balls.size(), getPosition().x, getPosition().y);
    }
}
