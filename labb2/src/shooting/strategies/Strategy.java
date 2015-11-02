package shooting.strategies;

import shooting.Duel;
import shooting.Player;

public abstract class Strategy {
    protected final Duel duel;

    public Strategy(Duel duel) {
        this.duel = duel;
    }

    public abstract Player choose(Player current);
}
