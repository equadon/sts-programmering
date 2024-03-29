package labb4.game.tables;

import labb4.game.Config;
import labb4.game.GameType;
import labb4.game.Player;
import labb4.game.Vector2D;
import labb4.game.interfaces.Aimable;
import labb4.game.interfaces.GameListener;
import labb4.game.interfaces.Placeable;
import labb4.game.objects.Ball;
import labb4.game.objects.CueBall;
import labb4.game.objects.Pocket;
import labb4.game.objects.PoolBall;
import labb4.game.ui.painters.TablePainter;

import java.awt.*;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Table {
    private final TablePainter painter;

    private final List<GameListener> listeners;

    private final Rectangle bounds;
    private final Rectangle playableBounds;

    protected List<PoolBall> balls;
    protected Pocket[] pockets;

    public final int width;
    public final int height;
    public final int outerBorderSize;
    public final int innerBorderSize;

    private final Player[] players;
    private int currentPlayerId;

    private Polygon[] collisionBounds;

    public final int topLeft1CollisionX;
    public final int topRight1CollisionX;

    // Observer fields
    protected Map<PoolBall, Pocket> pocketedBalls;

    public Table(int width, int height, Player[] players) {
        this.players = players;

        currentPlayerId = 0;

        outerBorderSize = (int) (11 * Config.RESIZE_FACTOR + 1);
        innerBorderSize = (int) (7 * Config.RESIZE_FACTOR);

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

        pocketedBalls = new HashMap<>();

        topLeft1CollisionX = playableBounds.x + 32;
        topRight1CollisionX = (int) playableBounds.getCenterX() - Config.DEFAULT_POCKET_RADIUS - 5;

        updateCollisionBounds();
    }

    public Polygon[] getCollisionBounds() {
        return collisionBounds;
    }

    protected void updateCollisionBounds() {
        Rectangle playable = getPlayableBounds();
        Point center = new Point((int) playable.getCenterX(), (int) playable.getCenterY());

        collisionBounds = new Polygon[] {
                new Polygon(
                        new int[] {playable.x + 9, center.x - Config.DEFAULT_POCKET_RADIUS + 2, center.x - Config.DEFAULT_POCKET_RADIUS - 5, topLeft1CollisionX},
                        new int[] {playable.y - innerBorderSize, playable.y - innerBorderSize, playable.y, playable.y},
                        4
                ), new Polygon(
                        new int[] {center.x + Config.DEFAULT_POCKET_RADIUS - 1, (int) playable.getMaxX() - 9, (int) playable.getMaxX() - 32, center.x + Config.DEFAULT_POCKET_RADIUS + 4},
                        new int[] {playable.y - innerBorderSize, playable.y - innerBorderSize, playable.y, playable.y},
                        4
                ), new Polygon(
                        new int[] {playable.x + 9, center.x - Config.DEFAULT_POCKET_RADIUS + 2, center.x - Config.DEFAULT_POCKET_RADIUS - 5, playable.x + 32},
                        new int[] {(int) playable.getMaxY() + innerBorderSize, (int) playable.getMaxY() + innerBorderSize, (int) playable.getMaxY(), (int) playable.getMaxY()},
                        4
                ), new Polygon(
                        new int[] {center.x + Config.DEFAULT_POCKET_RADIUS - 1, (int) playable.getMaxX() - 9, (int) playable.getMaxX() - 32, center.x + Config.DEFAULT_POCKET_RADIUS + 4},
                        new int[] {(int) playable.getMaxY() + innerBorderSize, (int) playable.getMaxY() + innerBorderSize, (int) playable.getMaxY(), (int) playable.getMaxY()},
                        4
                ), new Polygon(
                        new int[] {playable.x, playable.x - innerBorderSize, playable.x - innerBorderSize, playable.x},
                        new int[] {playable.y + Config.DEFAULT_POCKET_RADIUS + 7, playable.y + Config.DEFAULT_POCKET_RADIUS - 12, (int) playable.getMaxY() - 8, (int) playable.getMaxY() - Config.DEFAULT_POCKET_RADIUS - 10},
                        4
                ), new Polygon(
                        new int[] {(int) playable.getMaxX(), (int) playable.getMaxX() + innerBorderSize, (int) playable.getMaxX() + innerBorderSize, (int) playable.getMaxX()},
                        new int[] {playable.y + Config.DEFAULT_POCKET_RADIUS + 7, playable.y + Config.DEFAULT_POCKET_RADIUS - 12, (int) playable.getMaxY() - 8, (int) playable.getMaxY() - Config.DEFAULT_POCKET_RADIUS - 10},
                        4
                ),
        };
    }

    public boolean isUpdating() {
        for (PoolBall ball : balls) {
            if (ball.isMoving()) {
                return true;
            }
        }

        return false;
    }

    public Player[] getPlayers() {
        return players;
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
    }

    protected Pocket[] createPockets() {
        pockets = new Pocket[Config.POCKET_COUNT];
        Vector2D position;

        double width = playableBounds.width;
        double height = playableBounds.height;

        int offset = (int) (innerBorderSize / 2.0);

        for (int i = 0; i < pockets.length; i++) {
            int row = i / 3;
            int col = i % 3;

            int x = 0;
            int y = 0;

            if (row == 0 && col != 1) {
                y += offset;
            } else if (row == 1 && col != 1) {
                y -= offset;
            }

            if (col == 0) {
                x += offset;
            } else if (col == 2) {
                x -= offset;
            }

            position = new Vector2D(playableBounds.x + col * (width / 2.0) + x, playableBounds.y + row * height + y);

            pockets[i] = new Pocket(this, position, Config.DEFAULT_POCKET_RADIUS);
        }

        return pockets;
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

    public boolean checkCollision(Ball ball) {
        Rectangle.Double bBounds = ball.getBounds();

        if (bBounds.y < playableBounds.y ||
                bBounds.getMaxY() > playableBounds.getMaxY() ||
                bBounds.x < playableBounds.x ||
                bBounds.getMaxX() > playableBounds.getMaxX()) {
            for (Polygon polygon : collisionBounds) {
                Area area1 = new Area(polygon);
                Area area2 = new Area(polygon);
                Area area3 = new Area(polygon);
                Area area4 = new Area(polygon);
                area1.intersect(new Area(ball.upperLeftBounds));
                area2.intersect(new Area(ball.upperRightBounds));
                area3.intersect(new Area(ball.lowerLeftBounds));
                area4.intersect(new Area(ball.lowerRightBounds));

                // Check pocket collisions
                if (bBounds.x < playableBounds.x && bBounds.y < playableBounds.y) {
                    pockets[0].add((PoolBall) ball);
                    break;
                } else if (bBounds.getMaxX() > playableBounds.getMaxX() && bBounds.y < playableBounds.y) {
                    pockets[2].add((PoolBall) ball);
                    break;
                } else if (bBounds.getCenterX() > playableBounds.x && bBounds.getCenterX() < playableBounds.getMaxX() && bBounds.getCenterY() < playableBounds.y) {
                    pockets[1].add((PoolBall) ball);
                    break;
                } else if (bBounds.x < playableBounds.x && bBounds.getCenterY() > playableBounds.getMaxY()) {
                    pockets[3].add((PoolBall) ball);
                } else if (bBounds.getMaxX() > playableBounds.getMaxX() && bBounds.getCenterY() > playableBounds.getMaxY()) {
                    pockets[5].add((PoolBall) ball);
                    break;
                } else if (bBounds.getCenterX() > playableBounds.x && bBounds.getCenterX() < playableBounds.getMaxX() && bBounds.getCenterY() > playableBounds.getMaxY()) {
                    pockets[4].add((PoolBall) ball);
                    break;
                }

                if (handleTopSide(ball, area1, area2, area3, area4))
                    break;

                if (handleBottomSide(ball, area1, area2, area3, area4))
                    break;

                if (handleLeftSide(ball, area1, area2, area3, area4))
                    break;

                if (handleRightSide(ball, area1, area2, area3, area4))
                    break;
            }
        }

        return false;
    }

    private boolean handleRightSide(Ball ball, Area area1, Area area2, Area area3, Area area4) {
        if (ball.getVelocity().x < 0 || (!area1.isEmpty() || !area3.isEmpty()))
            return false;

        Rectangle.Double bBounds = ball.getBounds();
        if (area2.isEmpty() && !area4.isEmpty()) {
            // right diag up
            Vector2D newVelocity = calcDiagonalVelocity(ball, 1, -1);
            ball.setVelocity(newVelocity.x, newVelocity.y);
            return true;
        } else if (!area2.isEmpty() && area4.isEmpty()) {
            // right diag down
            Vector2D newVelocity = calcDiagonalVelocity(ball, 1, 1);
            ball.setVelocity(newVelocity.x, newVelocity.y);
            return true;
        } else if (!area2.isEmpty() && !area4.isEmpty()) {
            // clean right wall
            handleRightWallCollision(ball);
            return true;
        }
        return false;
    }

    private boolean handleLeftSide(Ball ball, Area area1, Area area2, Area area3, Area area4) {
        if (ball.getVelocity().x > 0 || (!area2.isEmpty() || !area4.isEmpty()))
            return false;

        Rectangle.Double bBounds = ball.getBounds();
        if (area1.isEmpty() && !area3.isEmpty()) {
            // left diag up
            Vector2D newVelocity = calcDiagonalVelocity(ball, -1, -1);
            ball.setVelocity(newVelocity.x, newVelocity.y);
            return true;
        } else if (!area1.isEmpty() && area3.isEmpty()) {
            // left diag down
            Vector2D newVelocity = calcDiagonalVelocity(ball, -1, 1);
            ball.setVelocity(newVelocity.x, newVelocity.y);
            return true;
        } else if (!area1.isEmpty() && !area3.isEmpty()) {
            // clean left wall
            handleLeftWallCollision(ball);
            return true;
        }
        return false;
    }

    private boolean handleBottomSide(Ball ball, Area area1, Area area2, Area area3, Area area4) {
        if (ball.getVelocity().y < 0 || (!area1.isEmpty() || !area2.isEmpty()))
            return false;

        Rectangle.Double bBounds = ball.getBounds();
        if (area3.isEmpty() && !area4.isEmpty()) {
            // bottom diag left
            Vector2D newVelocity = calcDiagonalVelocity(ball, -1, 1);
            ball.setVelocity(newVelocity.x, newVelocity.y);
            return true;
        } else if (!area3.isEmpty() && area4.isEmpty()) {
            // bottom diag right
            Vector2D newVelocity = calcDiagonalVelocity(ball, 1, 1);
            ball.setVelocity(newVelocity.x, newVelocity.y);
            return true;
        } else if (!area3.isEmpty() && !area4.isEmpty()) {
            // clean bottom wall
            handleBottomWallCollision(ball);
            return true;
        }
        return false;
    }

    private boolean handleTopSide(Ball ball, Area area1, Area area2, Area area3, Area area4) {
        if (ball.getVelocity().y > 0 || (!area3.isEmpty() || !area4.isEmpty()))
            return false;

        Rectangle.Double bBounds = ball.getBounds();
        if (area1.isEmpty() && !area2.isEmpty()) {
            // Top diag left
            Vector2D newVelocity = calcDiagonalVelocity(ball, -1, -1);
            ball.setVelocity(newVelocity.x, newVelocity.y);
            return true;
        } else if (!area1.isEmpty() && area2.isEmpty()) {
            // top diag right
            Vector2D newVelocity = calcDiagonalVelocity(ball, 1, -1);
            ball.setVelocity(newVelocity.x, newVelocity.y);
            return true;
        } else if (!area1.isEmpty() && !area2.isEmpty()) {
            // clean top wall
            handleTopWallCollision(ball);
            return true;
        }
        return false;
    }

    private Vector2D calcDiagonalVelocity(Ball ball, double x, double y) {
        Vector2D velocity = ball.getVelocity();
        Vector2D newVelocity = new Vector2D(x, y).normalize().multiply(velocity.length());

        return newVelocity;
    }

    private void handleLeftWallCollision(Ball ball) {
        ball.getPosition().x += playableBounds.getX() - ball.getBounds().x;
        Vector2D velocity = ball.getVelocity();
        ball.setVelocity(-velocity.x, velocity.y);
    }
    private void handleRightWallCollision(Ball ball) {
        Vector2D velocity = ball.getVelocity();
        ball.setVelocity(-velocity.x, velocity.y);
    }
    private void handleTopWallCollision(Ball ball) {
        ball.getPosition().y = playableBounds.getY() + ball.getRadius();
        Vector2D velocity = ball.getVelocity();
        ball.setVelocity(velocity.x, -velocity.y);
    }
    private void handleBottomWallCollision(Ball ball) {
        ball.getPosition().y -= ball.getBounds().getMaxY() - playableBounds.getMaxY();
        Vector2D velocity = ball.getVelocity();
        ball.setVelocity(velocity.x, -velocity.y);
    }

    /**
     * Observer pattern methods.
     */
    public void addListener(GameListener listener) {
        listeners.add(listener);
    }
    public void removeListener(GameListener listener) {
        listeners.remove(listener);
    }

    public abstract void newGame(Player starting);
    public abstract void beginTurn();
    public abstract void collision(PoolBall ball1, PoolBall ball2);
    public abstract void pocketed(PoolBall ball, Pocket pocket);
    public abstract void endTurn();

    /**
     * Notification methods.
     */
    protected void notifyIllegalMove(String reason) {
        for (GameListener o : listeners)
            o.illegalMove(reason);
    }

    protected void notifyPlayerChange(Player newPlayer) {
        for (GameListener o : listeners)
            o.playerChanged(newPlayer);
    }

    protected void notifyAddPoints(Player player, int points) {
        for (GameListener o : listeners)
            o.pointsAdded(player, points);
    }

    protected void notifyPlacingBall(Placeable placeable) {
        for (GameListener o : listeners)
            o.placeStarted(placeable);
    }

    protected void notifyNextBall(String next) {
        for (GameListener o : listeners)
            o.nextBall(next);
    }

    protected void notifyGameOver(Player winner) {
        for (GameListener o : listeners)
            o.gameEnded(winner);
    }

    protected boolean isCueBallPocketed() {
        for (Map.Entry<PoolBall, Pocket> entry : pocketedBalls.entrySet()) {
            if (entry.getKey() instanceof CueBall) {
                return true;
            }
        }
        return false;
    }

    protected boolean isBallPocketed(int number) {
        for (Map.Entry<PoolBall, Pocket> entry : pocketedBalls.entrySet()) {
            if (entry.getKey().number == number) {
                return true;
            }
        }
        return false;
    }

    protected void placeAllPocketedBalls() {
        for (Map.Entry<PoolBall, Pocket> entry : pocketedBalls.entrySet()) {
            PoolBall ball = entry.getKey();
            Pocket pocket = entry.getValue();

            pocket.remove(ball);

            notifyPlacingBall(ball);
        }
    }
}
