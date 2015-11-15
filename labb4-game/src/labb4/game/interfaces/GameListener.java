package labb4.game.interfaces;

import labb4.game.Player;

public interface GameListener {
    void illegalMove(String reason);
    void changedPlayer(Player player);
    void addPoints(Player player, int points);
    void gameOver(Player winner);
}
