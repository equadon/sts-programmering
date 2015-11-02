package shooting.strategies;

import shooting.Duel;
import shooting.Player;

public class ChooseNobodyStrategy extends Strategy {
    public ChooseNobodyStrategy(Duel duel) {
        super(duel);
    }

    @Override
    public Player choose(Player current) {
        int countBetter = 0;
        Player chosen = null;

        for (Player player : duel.getPlayers()) {
            if (player != current) {
                if (player.accuracy > current.accuracy) {
                    countBetter++;
                    if (chosen == null || player.accuracy > chosen.accuracy)
                        chosen = player;
                }
            }
        }

        if (countBetter > 1)
            chosen = null;

        return chosen;
    }
}
