package labb4.game.tables;

import labb4.game.Config;
import labb4.game.GameType;
import labb4.game.Player;
import labb4.game.Vector2D;
import labb4.game.handlers.GameHandler;
import labb4.game.interfaces.Aimable;
import labb4.game.interfaces.GameObserver;
import labb4.game.objects.Pocket;
import labb4.game.objects.PoolBall;
import labb4.game.ui.painters.TablePainter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Table {
    private final TablePainter painter;

    private final Rectangle bounds;
    private final Rectangle playableBounds;

    protected List<PoolBall> balls;
    protected Pocket[] pockets;

    protected final List<GameObserver> listeners;

    public final int width;
    public final int height;
    public final int outerBorderSize;
    public final int innerBorderSize;

    // Info messages
    private String topText;
    private String bottomText;

    protected GameHandler handler;

    private final Player[] players;
    private int currentPlayerId;

    public Table(int width, int height, GameHandler handler, Player[] players) {
        this.handler = handler;
        this.players = players;

        currentPlayerId = 0;

        outerBorderSize = Config.TABLE_OUTER_BORDER_SIZE;
        innerBorderSize = Config.TABLE_INNER_BORDER_SIZE;

        this.width = width + 2 * (outerBorderSize + innerBorderSize);
        this.height = height + 2 * (outerBorderSize + innerBorderSize);

        painter = new TablePainter();

        bounds = new Rectangle(0, 0, this.width, this.height);
        playableBounds = new Rectangle(
                outerBorderSize + innerBorderSize,
                outerBorderSize + innerBorderSize,
                this.width - 2 * (outerBorderSize + innerBorderSize),
                this.height - 2 * (outerBorderSize + innerBorderSize)
        );

        listeners = new ArrayList<>();

        pockets = createPockets();
        balls = createBalls();
    }

    public boolean isUpdating() {
        for (PoolBall ball : balls) {
            if (ball.isMoving()) {
                return true;
            }
        }

        return false;
    }

    public String getTopText() {
        return topText;
    }

    public String getBottomText() {
        return bottomText;
    }

    public GameHandler getHandler() {
        return handler;
    }

    public Rectangle getBounds() {
        return bounds;
    }
    public Rectangle getPlayableBounds() {
        return playableBounds;
    }

    public PoolBall[] getBalls() {
        return balls.toArray(new PoolBall[balls.size()]);
    }
    public Pocket[] getPockets() {
        return pockets;
    }

    public Aimable[] getAimable() {
        List<Aimable> aimables = new ArrayList<>();
        for (PoolBall ball : getBalls()) {
            if (ball instanceof Aimable) {
                aimables.add((Aimable) ball);
            }
        }

        return aimables.toArray(new Aimable[aimables.size()]);
    }

    public Player getCurrentPlayer() {
        return players[currentPlayerId];
    }

    public void draw(Graphics2D g) {
        painter.draw(g, this);

        for (Pocket pocket : pockets) {
            pocket.draw(g);
        }

        for (PoolBall ball : balls) {
            if (ball.isVisible()) {
                ball.draw(g);
            }
        }
    }

    public void update() {
        for (PoolBall ball : balls) {
            if (ball.isVisible()) {
                ball.update();
            }
        }

        for (PoolBall ball : getBalls()) {
            if (!ball.isPlacing() && ball.isVisible()) {
                ball.handleCollisions();
            }
        }
    }

    public void newGame() {
        handler.newGame(getCurrentPlayer());
    }

    public void endTurn() {
        nextPlayer();
        handler.endTurn(getCurrentPlayer());
    }

    protected void nextPlayer() {
        currentPlayerId = (currentPlayerId + 1) % players.length;
    }

    /**
     * Ball pocketed, remove from list.
     */
    public void remove(PoolBall ball) {
        balls.remove(ball);
    }
    public void add(PoolBall ball) {
        if (!balls.contains(ball)) {
            balls.add(ball);
        }
    }

    protected List<PoolBall> createBalls() {
        return new ArrayList<>();
    };

    protected Pocket[] createPockets() {
        pockets = new Pocket[Config.POCKET_COUNT];
        Vector2D position;

        double width = playableBounds.width;
        double height = playableBounds.height;

        for (int i = 0; i < pockets.length; i++) {
            int row = i / 3;
            int col = i % 3;

            position = new Vector2D(playableBounds.x + col * (width / 2.0), playableBounds.y + row * height);

            pockets[i] = new Pocket(this, position, Config.DEFAULT_POCKET_RADIUS);
        }

        return pockets;
    }

    public void addObserver(GameObserver observer) {
        handler.addObserver(observer);
    }
    public void removeObserver(GameObserver observer) {
        handler.removeObserver(observer);
    }

    /**
     * Helper method to create a Table object based on chosen game type.
     */
    public static Table createTable(GameType type, Player[] players) {
        switch (type) {
            case EightBall:
                return new EightBallTable(players);

            case NineBall:
                return new NineBallTable(players);

            case Snooker:
                return new SnookerTable(players);
        }

        return null;
    }
}
