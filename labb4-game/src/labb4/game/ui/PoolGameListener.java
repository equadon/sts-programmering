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
        panel.setTurn(player.name + "'s turn!");
        panel.repaint();
    }

    @Override
    public void addPoints(Player player, int points) {
        player.addPoints(points);
    }

    @Override
    public void updateMessage(String message) {
        panel.setMessage(message);
        panel.repaint();
    }

    @Override
    public void updateTurnText(String message) {
        panel.setTurnText(message);
        panel.repaint();
    }

    @Override
    public void gameOver(Player winner) {
        panel.setTurn("Winner: " + winner.name);
        panel.setMessage(winner.name + " won!");
        panel.setTurnText("Game Over");
        panel.repaint();

        JOptionPane.showMessageDialog(panel, winner.name + " won the game!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }
}
