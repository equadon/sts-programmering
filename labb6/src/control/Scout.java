package control;

import java.awt.*;
import java.awt.geom.Area;

public class Scout extends HeavyPiece {
    final private int OSIZE = board.SIZE / 2;

    Scout(boolean w, boolean my, Board.Position pos, Board board) {
        super(w, my, pos, board);

        MAXIMUM = 1;
        stride = board.SIZE * 2;
        range = board.SIZE * 2;
        power = 3;
    }

    @Override
    Area appearance(Board.Position pos) {
        return new Area(new Polygon(
                new int[] {pos.x, pos.x + OSIZE / 2, pos.x - OSIZE / 2},
                new int[] {pos.y - OSIZE / 2, pos.y + OSIZE / 2, pos.y + OSIZE / 2},
                3
        ));
    }

    @Override
    boolean canMove(Board.Position p) {
        if (p != null && board.canMoveTo(p)) {             // Can only move to highlighted area
            return !p.equals(pos);                         // but not to the same position!
        }

        return false;
    }
}
