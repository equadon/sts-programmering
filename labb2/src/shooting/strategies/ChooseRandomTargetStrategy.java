package shooting.strategies;

import shooting.Duel;
import shooting.Player;

import java.util.Random;

public class ChooseRandomTargetStrategy extends Strategy {
    private final Random random;

    public ChooseRandomTargetStrategy(Duel duel) {
        super(duel);
        this.random = new Random();
    }

    @Override
    public Player choose(Player current) {
        Player chosen = current;
        while (chosen == current)
            chosen = getRandomPlayer();

        return chosen;
    }

    public Player getRandomPlayer() {
        Player[] players = duel.getPlayers();

        return players[random.nextInt(players.length)];
    }
}
