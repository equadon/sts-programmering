package labb4.game.ui.painters;

import labb4.game.Config;
import labb4.game.Player;
import labb4.game.Vector2D;
import labb4.game.tables.SnookerTable;
import labb4.game.tables.Table;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TablePainter {
    public void draw(Graphics2D g, Table table) {
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
            drawSnookerLines(g, playableBounds);
        } else {
            drawStandardLines(g, playableBounds);
        }
    }

    private void drawStandardLines(Graphics2D g, Rectangle bounds) {
        double xLine = Config.DEFAULT_X_LINE * bounds.getMaxX();

        g.setColor(Config.TABLE_LINE_COLOR);
        g.fillRect((int) xLine, bounds.y, Config.LINE_SIZE, bounds.height);
    }

    private void drawSnookerLines(Graphics2D g, Rectangle bounds) {
        int height = bounds.height;

        Vector2D center = new Vector2D(bounds.getCenterX(), bounds.getCenterY());
        double xLine = Config.SNOOKER_X_LINE * bounds.getMaxX();

        g.setColor(Config.TABLE_LINE_COLOR);
        g.fillRect((int) xLine, bounds.y, Config.LINE_SIZE, height);

        double arcDiameter = 0.3 * height + 2 * Config.BALL_RADIUS - 2*Config.LINE_SIZE;

        g.setStroke(new BasicStroke(Config.LINE_SIZE));
        g.drawArc((int) (xLine - arcDiameter / 2.0), (int) (center.y - arcDiameter / 2.0), (int) arcDiameter, (int) arcDiameter, 90, 180);
    }
}
