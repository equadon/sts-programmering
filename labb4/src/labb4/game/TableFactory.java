package labb4.game;

import labb4.game.initializers.BallInitializer;
import labb4.game.initializers.EightBallInitializer;
import labb4.game.initializers.SnookerInitializer;

public class TableFactory {
    public static Table createStandardPoolTable(GameType type) {
        BallInitializer initializer = null;

        switch (type) {
            case EightBall:
                initializer = new EightBallInitializer();
                break;

            case Snooker:
                initializer = new SnookerInitializer();
                break;
        }

        return new Table(127, 254, initializer);
    }

    public static Table createSnookerTable() {
        return new Table(142, 284, new SnookerInitializer());
    }
}
