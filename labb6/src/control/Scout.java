package control;

import java.awt.*;
import java.awt.geom.Area;

public class Scout extends HeavyPiece {
    private static final int SIDE = 30;

    Scout(boolean w, boolean my, Board.Position pos, Board board) {
        super(w, my, pos, board);
    }

    @Override
    Area appearance(Board.Position pos) {
        return new Area(new Polygon(
                new int[] {pos.x, pos.x + SIDE, pos.x + SIDE, pos.x},
                new int[] {pos.y, pos.y, pos.y + SIDE, pos.y + SIDE},
                4
        ));
    }
}
