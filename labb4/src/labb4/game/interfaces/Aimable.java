package labb4.game.interfaces;

import labb4.game.Vector2D;

public interface Aimable {
    void shoot();
    void setAim(Vector2D startPosition);
    void updateAim(Vector2D newPosition);
}
