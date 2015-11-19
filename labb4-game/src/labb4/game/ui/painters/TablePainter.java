package labb4.game.ui.painters;

import labb4.game.Config;
import labb4.game.Vector2D;
import labb4.game.tables.SnookerTable;
import labb4.game.tables.Table;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Area;
import java.io.File;
import java.io.IOException;

public class TablePainter {
    public static final int SPOT_RADIUS = 5;

    private Image poolTableImage;
    private Image snookerTableImage;

    public static Shape area;

    public TablePainter() {
        try {
            poolTableImage = ImageIO.read(new File("gfx/PoolTable.png"));
            snookerTableImage = ImageIO.read(new File("gfx/SnookerTable.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g, Table table) {
        Rectangle bounds = table.getPlayableBounds();

        if (table instanceof SnookerTable) {
            g.drawImage(snookerTableImage, 0, 0, table.width, table.height, null);
        } else {
            g.drawImage(poolTableImage, 0, 0, table.width, table.height, null);
        }

        g.setColor(Config.TABLE_LINE_COLOR);
        if (table instanceof SnookerTable) {
            drawSnookerLines(g, bounds, bounds.width);
        } else {
            drawStandardLines(g, bounds);
        }

        if (Config.DISPLAY_BOUNDING_BOXES) {
            g.setColor(new Color(1f, 0, 0, 0.3f));
            g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);

            g.setColor(new Color(255, 0, 0, 138));
            for (Polygon polygon : table.getCollisionBounds()) {
                g.fillPolygon(polygon);
            }
        }
    }

    public void draw2(Graphics2D g, Table table) {
        Rectangle bounds = table.getBounds();
        Rectangle playableBounds = table.getPlayableBounds();

        // Borders
        g.setColor(Config.TABLE_OUTER_BORDER_COLOR);
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);

        g.setColor(Config.TABLE_INNER_BORDER_COLOR);
        g.fillRect(
                bounds.x + table.outerBorderSize,
                bounds.y + table.outerBorderSize,
                bounds.width - 2 * table.outerBorderSize,
                bounds.height - 2 * table.outerBorderSize
        );

        g.setColor(Config.TABLE_COLOR);
        g.fillRect(
                playableBounds.x,
                playableBounds.y,
                playableBounds.width,
                playableBounds.height
        );

        // Lines
        double xLine;
        if (table instanceof SnookerTable) {
            drawSnookerLines(g, playableBounds, bounds.width);
        } else {
            drawStandardLines(g, playableBounds);
        }
    }

    private void drawStandardLines(Graphics2D g, Rectangle bounds) {
        double xLine = Config.DEFAULT_X_LINE * bounds.getMaxX();

        g.setColor(Config.TABLE_LINE_COLOR);
        g.fillRect((int) xLine, bounds.y, Config.LINE_SIZE, bounds.height);
    }

    private void drawSnookerLines(Graphics2D g, Rectangle bounds, int fullWidth) {
        int height = bounds.height;

        Vector2D center = new Vector2D(bounds.getCenterX(), bounds.getCenterY());
        double xLine = Config.SNOOKER_X_LINE * bounds.getMaxX();

        g.setColor(Config.TABLE_LINE_COLOR);
        g.fillRect((int) xLine, bounds.y, Config.LINE_SIZE, height);

        double arcDiameter = 0.3 * height + 2 * Config.BALL_RADIUS - 2*Config.LINE_SIZE;

        g.setStroke(new BasicStroke(Config.LINE_SIZE));
        g.drawArc((int) (xLine - arcDiameter / 2.0), (int) (center.y - arcDiameter / 2.0), (int) arcDiameter, (int) arcDiameter, 90, 180);

        // Ball placement spots
        g.setColor(Config.TABLE_SPOT_COLOR);

        // Blue ball
        g.fillOval((int) bounds.getCenterX() - SPOT_RADIUS, (int) bounds.getCenterY() - SPOT_RADIUS, 2 * SPOT_RADIUS, 2 * SPOT_RADIUS);

        // Pink ball
        g.fillOval(
                (int) (2 * fullWidth / 3.0 - SPOT_RADIUS - Config.BALL_RADIUS / 4.0),
                (int) bounds.getCenterY() - SPOT_RADIUS,
                2 * SPOT_RADIUS,
                2 * SPOT_RADIUS
        );

        // Black ball
        g.fillOval(
                (int) ((1-0.125) * fullWidth - SPOT_RADIUS),
                (int) bounds.getCenterY() - SPOT_RADIUS,
                2 * SPOT_RADIUS,
                2 * SPOT_RADIUS
        );
    }
}
