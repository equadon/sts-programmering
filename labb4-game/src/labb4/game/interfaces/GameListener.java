package labb4.game.interfaces;

import labb4.game.Player;
import labb4.game.objects.PoolBall;

public interface GameListener {
    void illegalMove(String reason);
    void changedPlayer(Player player);
    void addPoints(Player player, int points);
    void updateMessage(String message);
    void updateTurnText(String message);
    void startPlacingBall(PoolBall ball);
    void gameOver(Player winner);
}
