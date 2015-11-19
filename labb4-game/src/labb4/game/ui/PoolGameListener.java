package labb4.game.ui;

import labb4.game.Player;
import labb4.game.interfaces.GameListener;
import labb4.game.interfaces.Placeable;

import javax.swing.*;

public class PoolGameListener implements GameListener {
    private final PoolPanel panel;

    public PoolGameListener(PoolPanel panel) {
        this.panel = panel;
    }

    @Override
    public void illegalMove(String reason) {
        System.out.println("Illegal move: " + reason);
    }

    @Override
    public void playerChanged(Player newPlayer) {
        System.out.println("Player change. New player: " + newPlayer.name);
    }

    @Override
    public void pointsAdded(Player player, int points) {
        System.out.println(player.name + " received " + points + " point(s).");
    }

    @Override
    public void placeStarted(Placeable placeable) {
        panel.startPlacing(placeable);
    }

    @Override
    public void gameEnded(Player winner) {
        System.out.println("Game Over! " + winner.name + " won.");
    }
}
