package labb4.game.objects;

import labb4.game.Config;
import labb4.game.tables.Table;
import labb4.game.Vector2D;
import labb4.game.ui.painters.PocketPainter;

import java.util.ArrayList;
import java.util.List;

public class Pocket extends GameObject {
    private final Table table;
    private final double radius;

    private final List<PoolBall> balls;

    public Pocket(Table table, Vector2D position, double radius) {
        super(new PocketPainter(), position, new Vector2D(0, 0), Config.DEFAULT_POCKET_COLOR, 0, 0);

        this.table = table;
        this.radius = radius;

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
        if (getPosition().distanceTo(ball.getPosition()) < radius) {
            PoolBall poolBall = (PoolBall) ball;

            pocketBall(poolBall);

            return true;
        }

        return false;
    }

    private void pocketBall(PoolBall ball) {
        balls.add(ball);

        table.remove(ball);
        ball.hide();
    }

    @Override
    protected void updateBounds() {
        bounds.x = getPosition().x - radius;
        bounds.y = getPosition().y - radius;
        bounds.width = 2 * radius;
        bounds.height = 2 * radius;
    }

    @Override
    public String toString() {
        return String.format("Pocket[balls=%d, pos=(%.0f,%.0f)]", balls.size(), getPosition().x, getPosition().y);
    }
}
