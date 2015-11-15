package labb4.game.ui;

import labb4.game.Player;
import labb4.game.interfaces.GameListener;

import javax.swing.*;
import java.util.logging.Logger;

public class PoolGameListener implements GameListener {
    private static final Logger LOG = Logger.getLogger(PoolGameListener.class.getName());

    private final PoolPanel panel;

    public PoolGameListener(PoolPanel panel) {
        this.panel = panel;
    }

    @Override
    public void illegalMove(String reason) {
        LOG.warning("Invalid move: " + reason);
    }

    @Override
    public void changedPlayer(Player player) {
        LOG.info("Current player: " + player.name);
        panel.setTurn(player);
        panel.repaint();
    }

    @Override
    public void addPoints(Player player, int points) {
        player.addPoints(points);
    }

    @Override
    public void gameOver(Player winner) {
        JOptionPane.showMessageDialog(panel, winner.name + " won the game!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        panel.getTurnLabel().setText("Winner: " + winner.name);
    }
}
