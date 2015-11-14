package labb4.game.ui;

import labb4.game.Table;
import labb4.game.Vector2D;
import labb4.game.objects.Ball;
import labb4.game.objects.Hole;
import labb4.game.objects.PoolBall;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PopClickListener extends MouseAdapter {
    private final PoolPanel panel;
    private final Table table;

    public PopClickListener(PoolPanel panel, Table table) {
        this.panel = panel;
        this.table = table;
    }

    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger()) {
            Vector2D position = Vector2D.fromMouseEvent(e);

            // Hole popup
            for (Hole hole : table.getHoles()) {
                if (hole.getBounds().contains(position.x, position.y)) {
                    holePopUp(e, hole);
                }
            }

            // Ball popup
            for (PoolBall ball : table.getBalls()) {
                if (ball.isVisible() && ball.getBounds().contains(position.x, position.y)) {
                    ballPopUp(e, ball);
                }
            }

            if (table.getCueBall().isVisible() && table.getCueBall().getBounds().contains(position.x, position.y)) {
                ballPopUp(e, table.getCueBall());
            }
        }
    }

    private void holePopUp(MouseEvent e, Hole hole){
        HoleContextMenu menu = new HoleContextMenu(panel, hole);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }

    private void ballPopUp(MouseEvent e, PoolBall ball){
        PoolBallContextMenu menu = new PoolBallContextMenu(panel, ball);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}
