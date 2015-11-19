package labb4.game.ui.contextmenus;

import labb4.game.objects.Pocket;
import labb4.game.objects.PoolBall;
import labb4.game.ui.GamePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PocketContextMenu extends JPopupMenu {
    public PocketContextMenu(GamePanel panel, Pocket pocket) {
        super();

        PoolBall[] balls = pocket.getBalls();

        add(new JMenuItem(pocket.toString()));
        addSeparator();

        if (balls.length < 1) {
            JMenuItem ballCount = new JMenuItem("Empty.");
            ballCount.setEnabled(false);
            add(ballCount);
        }

        if (balls.length > 0) {
            JMenuItem ballItem;
            int n = 1;
            for (PoolBall ball : balls) {
                String name = n + ". " + ball.toString();

                ballItem = new JMenuItem(new AbstractAction(name) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pocket.remove(ball);
                        panel.startPlacing(ball);
                    }
                });
                add(ballItem);
                n++;
            }
        }
    }
}
