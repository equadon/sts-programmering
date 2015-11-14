package labb4.game.ui;

import labb4.game.interfaces.Placeable;
import labb4.game.objects.CueBall;
import labb4.game.objects.Hole;
import labb4.game.objects.PoolBall;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class HoleContextMenu extends GameContextMenu {
    public HoleContextMenu(PoolPanel panel, Hole hole) {
        super();

        PoolBall[] balls = hole.getBalls();

        add(new JMenuItem(hole.toString()));
        addSeparator();

        if (balls.length < 1) {
            JMenuItem ballCount = new JMenuItem("Empty.");
            add(ballCount);
        }

        if (balls.length > 0) {
            JMenuItem ballItem;
            int n = 1;
            for (PoolBall ball : balls) {
                CueBall cueBall;
                String name = n + ". " + ball.toString();

                if (ball instanceof CueBall) {
                    cueBall = (CueBall) ball;
                    name = cueBall.toString();
                }

                ballItem = new JMenuItem(new AbstractAction(name) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        hole.remove(ball);
                        panel.startPlacing(ball);
                    }
                });
                add(ballItem);
                n++;
            }
        }
    }
}
