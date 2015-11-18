package labb4.game.interfaces;

import labb4.game.Player;

public interface GameListener {
    void illegalMove(String reason);
    void playerChanged(Player newPlayer);
    void pointsAdded(Player player, int points);
    void placeStarted(Placeable placeable);
    void gameEnded(Player winner);
}
