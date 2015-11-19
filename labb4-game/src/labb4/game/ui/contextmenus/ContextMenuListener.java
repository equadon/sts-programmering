package labb4.game.ui.contextmenus;

import labb4.game.tables.Table;
import labb4.game.Vector2D;
import labb4.game.objects.Pocket;
import labb4.game.objects.PoolBall;
import labb4.game.ui.GamePanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ContextMenuListener extends MouseAdapter {
    private final GamePanel panel;
    private final Table table;

    public ContextMenuListener(GamePanel panel, Table table) {
        this.panel = panel;
        this.table = table;
    }

    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger()) {
            Vector2D position = Vector2D.fromMouseEvent(e);

            // Pocket popup
            for (Pocket pocket : table.getPockets()) {
                if (pocket.getBounds().contains(position.x, position.y)) {
                    holePopUp(e, pocket);
                    return;
                }
            }

            // Ball popup
            for (PoolBall ball : table.getBalls()) {
                if (ball.isVisible() && ball.getBounds().contains(position.x, position.y)) {
                    ballPopUp(e, ball);
                    return;
                }
            }
        }
    }

    private void holePopUp(MouseEvent e, Pocket pocket){
        PocketContextMenu menu = new PocketContextMenu(panel, pocket);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }

    private void ballPopUp(MouseEvent e, PoolBall ball){
        PoolBallContextMenu menu = new PoolBallContextMenu(panel, ball);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}
