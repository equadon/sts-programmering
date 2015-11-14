package labb4.game;

import labb4.game.initializers.BallInitializer;
import labb4.game.initializers.EightBallInitializer;
import labb4.game.initializers.NineBallInitializer;
import labb4.game.initializers.SnookerInitializer;

public class TableFactory {
    public static Table createStandardPoolTable(GameType type) {
        BallInitializer initializer = null;

        switch (type) {
            case EightBall:
                initializer = new EightBallInitializer();
                break;

            case NineBall:
                initializer = new NineBallInitializer();
                break;

            case Snooker:
                initializer = new SnookerInitializer();
                break;
        }

        return new Table(Config.DEFAULT_TABLE_WIDTH, Config.DEFAULT_TABLE_HEIGHT, 20, 15, initializer);
    }

    public static Table createSnookerTable() {
        return new Table(Config.SNOOKER_TABLE_WIDTH, Config.SNOOKER_TABLE_HEIGHT, 20, 15, new SnookerInitializer());
    }
}
