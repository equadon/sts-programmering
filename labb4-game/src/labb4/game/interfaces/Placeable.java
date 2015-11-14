package labb4.game.interfaces;

import labb4.game.Vector2D;

public interface Placeable {
    boolean isPlacing();

    void startPlacing();
    void updatePlacement(Vector2D newPosition);
    boolean place(Vector2D newPosition);
}
