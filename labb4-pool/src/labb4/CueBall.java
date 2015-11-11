package labb4;

import labb4.painters.BallPainter;

import java.awt.*;

public class CueBall extends Ball {
    private Vector2D initialAimPosition;
    private Vector2D aimPosition;

    public CueBall(int x, int y, int radius, BallPainter painter) {
        super(x, y, radius, Color.WHITE, painter);
    }

    public Vector2D getAimPosition() {
        return aimPosition;
    }

    public Vector2D getInitialAimPosition() {
        return initialAimPosition;
    }

    public void setAimPosition(Vector2D position) {
        this.aimPosition = position;
        this.initialAimPosition = position;
    }

    public void updateAimPosition(Vector2D position) {
        this.aimPosition = position;
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        painter.drawAimPosition(g, this);
    }

    public void shoot() {
        aimPosition = null;
    }
}
