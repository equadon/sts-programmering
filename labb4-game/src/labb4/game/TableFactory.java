package labb4.game;

import labb4.game.handlers.EightBallHandler;
import labb4.game.handlers.GameHandler;
import labb4.game.handlers.NineBallHandler;
import labb4.game.handlers.SnookerHandler;
import labb4.game.initializers.BallInitializer;
import labb4.game.initializers.EightBallInitializer;
import labb4.game.initializers.NineBallInitializer;
import labb4.game.initializers.SnookerInitializer;

public class TableFactory {
    public static Table createPoolTable(GameType type, Player player1, Player player2) {
        BallInitializer initializer = null;
        GameHandler handler = null;

        int width = Config.DEFAULT_TABLE_WIDTH;
        int height = Config.DEFAULT_TABLE_HEIGHT;

        switch (type) {
            case EightBall:
                initializer = new EightBallInitializer();
                handler = new EightBallHandler();
                break;

            case NineBall:
                initializer = new NineBallInitializer();
                handler = new NineBallHandler();
                break;

            case Snooker:
                initializer = new SnookerInitializer();
                handler = new SnookerHandler();
                width = Config.SNOOKER_TABLE_WIDTH;
                height = Config.SNOOKER_TABLE_HEIGHT;
                break;
        }

        return new Table(width, height,
                Config.TABLE_OUTER_BORDER_SIZE, Config.TABLE_INNER_BORDER_SIZE,
                initializer, handler, player1, player2);
    }
}
