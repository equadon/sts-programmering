package labb4.game;

import labb4.game.initializers.BallInitializer;
import labb4.game.initializers.EightBallInitializer;
import labb4.game.initializers.NineBallInitializer;
import labb4.game.initializers.SnookerInitializer;

public class TableFactory {
    public static Table createPoolTable(GameType type, Player player1, Player player2) {
        BallInitializer initializer = null;

        int width = Config.DEFAULT_TABLE_WIDTH;
        int height = Config.DEFAULT_TABLE_HEIGHT;

        switch (type) {
            case EightBall:
                initializer = new EightBallInitializer();
                break;

            case NineBall:
                initializer = new NineBallInitializer();
                break;

            case Snooker:
                initializer = new SnookerInitializer();
                width = Config.SNOOKER_TABLE_WIDTH;
                height = Config.SNOOKER_TABLE_HEIGHT;
                break;
        }

        return new Table(width, height, 20, 15, initializer, player1, player2);
    }

    public static Table createSnookerTable(Player player1, Player player2) {
        return new Table(Config.SNOOKER_TABLE_WIDTH, Config.SNOOKER_TABLE_HEIGHT, 20, 15, new SnookerInitializer(), player1, player2);
    }
}
