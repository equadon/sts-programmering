package labb4.game.ui.painters;

import labb4.game.Config;
import labb4.game.Vector2D;
import labb4.game.tables.SnookerTable;
import labb4.game.tables.Table;

import java.awt.*;

public class TablePainter {
    private static final Font PLAYER_FONT = new Font("Ubuntu", Font.BOLD, 24);
    private static final Font MESSAGE_FONT = new Font("Ubuntu", Font.BOLD, 18);
    private static final Color FONT_COLOR = new Color(10, 134, 0);
    private static final Color MESSAGE_COLOR = new Color(10, 150, 0);

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
            xLine = Config.SNOOKER_X_LINE * playableBounds.getMaxX();
        } else {
            drawStandardLines(g, playableBounds);
            xLine = Config.DEFAULT_X_LINE * playableBounds.getMaxX();
        }

        if (table.getLeftText() != null) {
            printLeftText(g, PLAYER_FONT, FONT_COLOR, playableBounds, xLine, table.getLeftText());
        }

        if (table.getRightText() != null) {
            printRightText(g, MESSAGE_FONT, MESSAGE_COLOR, playableBounds, xLine, table.getRightText());
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

    private void printLeftText(Graphics2D g, Font font, Color color, Rectangle bounds, double xLine, String text) {
        float x = (float) (xLine - 5);
        float y = (float) bounds.getCenterY();

        g.rotate(-Math.PI / 2.0, x, y);
        printCenteredText(g, font, color, text, x, y);
        g.rotate(Math.PI / 2.0, x, y);
    }

    private void printRightText(Graphics2D g, Font font, Color color, Rectangle bounds, double xLine, String text) {
        FontMetrics metrics = g.getFontMetrics(font);

        float x = (float) (xLine + metrics.stringWidth(text) / 2.0) + 5;
        float y = (float) bounds.getMaxY() - 5;

        printCenteredText(g, font, color, text, x, y);
    }

    private void printCenteredText(Graphics2D g, Font font, Color color, String text, float x, float y) {
        FontMetrics metrics = g.getFontMetrics(font);

        x = (float) (x - metrics.stringWidth(text) / 2.0);

        g.setFont(font);
        g.setColor(color);
        g.drawString(text, x, y);
    }
}
