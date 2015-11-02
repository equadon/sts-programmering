package shooting.strategies;

import shooting.Duel;
import shooting.Player;

public class ChooseMostAccurateStrategy extends Strategy {
    public ChooseMostAccurateStrategy(Duel duel) {
        super(duel);
    }

    @Override
    public Player choose(Player current) {
        Player chosen = null;

        for (Player player : duel.getPlayers())
            if (player != current)
                if (chosen == null || player.accuracy > chosen.accuracy)
                    chosen = player;

        return chosen;
    }
}
