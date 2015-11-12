package labb4;

import labb4.painters.BallPainter;

import java.awt.*;

public class CueBall extends Ball {
    public CueBall(int x, int y, int radius, BallPainter painter) {
        super(x, y, radius, Color.WHITE, painter);
    }
}
