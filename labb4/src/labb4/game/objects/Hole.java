package labb4.game.objects;

import labb4.game.Config;
import labb4.game.Table;
import labb4.game.Vector2D;
import labb4.game.interfaces.Collidable;
import labb4.game.objects.GameObject;
import labb4.game.painters.HolePainter;

public class Hole extends GameObject implements Collidable {
    private final Table table;
    private final double radius;

    public Hole(Table table, Vector2D position, double radius) {
        super(new HolePainter(), position, new Vector2D(0, 0), Config.DEFAULT_HOLE_COLOR, 0, 0);

        this.table = table;
        this.radius = radius;

        updateBounds();
    }

    @Override
    public boolean handleCollisions() {
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
