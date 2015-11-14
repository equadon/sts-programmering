package labb4.game.ui;

import labb4.game.objects.Hole;
import labb4.game.objects.PoolBall;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class HoleContextMenu extends GameContextMenu {
    public HoleContextMenu(Hole hole) {
        super();

        PoolBall[] balls = hole.getBalls();

        JMenuItem ballCount = new JMenuItem("Balls: " + balls.length);
        add(ballCount);

        if (balls.length > 0) {
            addSeparator();

            JMenuItem ballItem;
            for (PoolBall ball : balls) {
                ballItem = new JMenuItem(new AbstractAction("Ball: " + ball.toString()) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null, "Ball: " + ball.toString(), "Ball Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                add(ballItem);
            }
        }
    }
}
