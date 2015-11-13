package labb4.game;

import labb4.game.interfaces.Collidable;
import labb4.game.painters.HolePainter;

public class Hole extends GameObject implements Collidable {
    private final Table table;

    public Hole(Table table, Vector2D position) {
        super(new HolePainter(), position, new Vector2D(0, 0), Config.DEFAULT_HOLE_COLOR, 0, 0);

        this.table = table;
    }

    @Override
    public boolean foundCollisions() {
        return false;
    }

    @Override
    public void handleCollisions() {}

    @Override
    protected void updateBounds() {

    }
}
