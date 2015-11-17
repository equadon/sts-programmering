package labb4.game.ui.painters;

import labb4.game.Config;
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

        // Line
        double yLine = (table instanceof SnookerTable) ?
                Config.SNOOKER_Y_LINE * playableBounds.getMaxY() :
                Config.DEFAULT_Y_LINE * playableBounds.getMaxY();

        g.setColor(Config.TABLE_LINE_COLOR);
        g.fillRect(playableBounds.x, (int) yLine, playableBounds.width, Config.LINE_SIZE);

        if (table.getTopText() != null) {
            //printCurrentPlayer(g, table.getTurnText(), playableBounds, yLine);
            printTopText(g, PLAYER_FONT, FONT_COLOR, playableBounds, yLine, table.getTopText());
        }

        if (table.getBottomText() != null) {
            //printMessage(g, table.getMessage(), playableBounds, yLine);
            printBottomText(g, MESSAGE_FONT, MESSAGE_COLOR, playableBounds, yLine, table.getBottomText());
        }
    }

    private void printTopText(Graphics2D g, Font font, Color color, Rectangle bounds, double yLine, String text) {
        FontMetrics metrics = g.getFontMetrics(font);

        float x = (float) bounds.getCenterX();
        float y = (float) yLine;

        printCenteredText(g, font, color, text, x, (float) (y - metrics.getHeight() / 5.0));
    }

    private void printBottomText(Graphics2D g, Font font, Color color, Rectangle bounds, double yLine, String text) {
        FontMetrics metrics = g.getFontMetrics(font);

        float x = (float) bounds.getCenterX();
        float y = (float) yLine + metrics.getHeight();

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
