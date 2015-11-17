package labb4.game.ui;

import labb4.game.Player;
import labb4.game.interfaces.GameObserver;
import labb4.game.interfaces.Placeable;

import javax.swing.*;

public class PoolGameObserver implements GameObserver {
    private final PoolPanel panel;

    public PoolGameObserver(PoolPanel panel) {
        this.panel = panel;
    }

    @Override
    public void illegalMove(String reason) {
        JOptionPane.showMessageDialog(panel, reason, "Invalid Move", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void changePlayer(Player newPlayer) {}

    @Override
    public void addPoints(Player player, int points) {
        player.addPoints(points);
    }

    @Override
    public void startPlacing(Placeable placeable) {
        panel.startPlacing(placeable);
    }

    @Override
    public void gameOver(Player winner) {
        panel.repaint();

        JOptionPane.showMessageDialog(panel, winner.name + " won the game!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }
}
