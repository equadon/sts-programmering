package labb4.game;

public interface Aimable {
    void shoot();
    void setAim(Vector2D startPosition);
    void updateAim(Vector2D newPosition);
}
