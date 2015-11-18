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
    public void illegalMove(String reason) {}

    @Override
    public void playerChanged(Player newPlayer) {}

    @Override
    public void pointsAdded(Player player, int points) {}

    @Override
    public void placeStarted(Placeable placeable) {}

    @Override
    public void gameEnded(Player winner) {}
}
