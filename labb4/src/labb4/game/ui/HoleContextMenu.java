package labb4.game.ui;

import labb4.game.objects.Hole;
import labb4.game.objects.PoolBall;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class HoleContextMenu extends GameContextMenu {
    public HoleContextMenu(Hole hole) {
        super();

        PoolBall[] balls = hole.getBalls();

        add(new JMenuItem("Hole: " + hole.toString()));
        addSeparator();

        if (balls.length < 1) {
            JMenuItem ballCount = new JMenuItem("Empty.");
            add(ballCount);
        }

        if (balls.length > 0) {
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
