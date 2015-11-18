package labb4.game.objects;

import labb4.game.tables.Table;
import labb4.game.Vector2D;
import labb4.game.interfaces.Placeable;

import java.awt.*;

public class PoolBall extends Ball implements Placeable {
    public final boolean showNumber;
    public final int number;

    private boolean placing;

    public PoolBall(Table table, Vector2D position, Color color, double radius, boolean striped, int number) {
        this(table, position, color, radius, striped, number, true);
    }

    public PoolBall(Table table, Vector2D position, Color color, double radius, boolean striped, int number, boolean showNumber) {
        super(table, position, color, radius, striped);

        this.number = number;
        this.showNumber = showNumber;
        placing = false;
    }

    @Override
    public boolean isPlacing() {
        return placing;
    }

    @Override
    public void startPlacing() {
        placing = true;
        velocity = new Vector2D(0, 0);
        show();
    }

    @Override
    public void updatePlacement(Vector2D newPosition) {
        setPosition(newPosition);
    }

    /**
     * Place ball on table.
     * @param newPosition
     * @return true if successful placement
     */
    @Override
    public boolean place(Vector2D newPosition) {
        Vector2D oldPosition = getPosition();
        setPosition(newPosition);

        if (table.getPlayableBounds().contains(getBounds()) && !collidesWithBall()) {
            velocity = new Vector2D(0, 0);
            placing = false;

            return true;
        } else {
            setPosition(oldPosition);

            return false;
        }
    }

    private boolean collidesWithBall() {
        for (Ball ball : table.getBalls()) {
            if (ball != this && ball.isVisible() && ball.getBounds().intersects(getBounds())) {
                if (getPosition().distanceTo(ball.getPosition()) < getRadius() + ball.getRadius()) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    protected void collidedWith(Ball other) {
        table.collision(this, (PoolBall) other);
    }

    @Override
    public String toString() {
        Vector2D position = getPosition();

        return String.format("PoolBall[number=%d, pos=(%.0f,%.0f), velocity=(%.0f,%.0f)]",
                number,
                position.x, position.y,
                velocity.x, velocity.y);
    }
}
