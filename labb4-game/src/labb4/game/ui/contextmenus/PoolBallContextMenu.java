package labb4.game.ui.contextmenus;

import labb4.game.objects.CueBall;
import labb4.game.objects.PoolBall;
import labb4.game.ui.GamePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PoolBallContextMenu extends JPopupMenu {
    public PoolBallContextMenu(GamePanel panel, PoolBall ball) {
        super();

        String name = ball.toString();
        if (ball instanceof CueBall) {
            name = ((CueBall) ball).toString();
        }

        add(new JMenuItem(ball.toString()));
        addSeparator();

        add(new JMenuItem(new AbstractAction("Move ball") {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.startPlacing(ball);
            }
        }));
    }
}
