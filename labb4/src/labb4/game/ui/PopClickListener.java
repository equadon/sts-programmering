package labb4.game.ui;

import labb4.game.Table;
import labb4.game.Vector2D;
import labb4.game.objects.Hole;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PopClickListener extends MouseAdapter {
    private final Table table;

    public PopClickListener(Table table) {
        this.table = table;
    }

    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger()) {
            Vector2D position = Vector2D.fromMouseEvent(e);

            // Hole popup?
            for (Hole hole : table.getHoles()) {
                if (hole.getBounds().contains(position.x, position.y)) {
                    doHolePop(e, hole);
                }
            }
        }
    }

    private void doHolePop(MouseEvent e, Hole hole){
        GameContextMenu menu = new HoleContextMenu(hole);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}
