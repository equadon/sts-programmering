package labb4;

import labb4.initializers.*;
import labb4.painters.TablePainter;

public class PoolTableFactory {
    public static PoolTable createTable(GameType gameType) {
        PoolTable table = null;

        switch (gameType) {
            case EIGHT_BALL:
                table = createStandardTable(new EightBallInitializer());
                break;

            case SNOOKER:
                table = createSnookerTable(new SnookerInitializer());
                break;
        }

        return table;
    }

    public static PoolTable createStandardTable(BallsInitializer ballsInitializer) {
        return new PoolTable(140, 270, ballsInitializer, new TablePainter());
    }

    public static PoolTable createSnookerTable(BallsInitializer ballsInitializer) {
        return new PoolTable(178, 357, ballsInitializer, new TablePainter());
    }
}
