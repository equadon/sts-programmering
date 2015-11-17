package labb4.game.interfaces;

import labb4.game.Player;
import labb4.game.objects.PoolBall;

public interface GameObserver {
    void illegalMove(String reason);
    void changePlayer(Player newPlayer);
    void addPoints(Player player, int points);
    void startPlacing(Placeable placeable);
    void gameOver(Player winner);
}
