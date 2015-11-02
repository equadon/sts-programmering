package shooting.strategies;

import shooting.Duel;
import shooting.Player;

public class ChooseLeastLivesStrategy extends Strategy {
    public ChooseLeastLivesStrategy(Duel duel) {
        super(duel);
    }

    @Override
    public Player choose(Player current) {
        Player chosen = null;

        for (Player player : duel.getPlayers())
            if (player != current)
                if (chosen == null || player.getLives() < chosen.getLives())
                    chosen = player;

        return chosen;
    }
}
