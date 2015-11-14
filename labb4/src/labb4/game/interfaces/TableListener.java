package labb4.game.interfaces;

import labb4.game.Player;

public interface TableListener {
    void turnChanged(Player currentPlayer);
    void pointsAdded(Player player, int points);
    void gameCreated();
}
