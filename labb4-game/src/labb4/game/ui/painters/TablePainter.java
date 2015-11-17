package labb4.game.ui.painters;

import labb4.game.Config;
import labb4.game.Table;

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
        double yLine = 3.0 * playableBounds.getMaxY() / 4.0;

        g.setColor(Config.TABLE_LINE_COLOR);
        g.fillRect(playableBounds.x, (int) yLine, playableBounds.width, 3);

        if (table.getTurnText() != null) {
            printCurrentPlayer(g, table.getTurnText(), playableBounds, yLine);
        }

        if (table.getMessage() != null) {
            printMessage(g, table.getMessage(), playableBounds, yLine);
        }
    }

    private void printCurrentPlayer(Graphics2D g, String message, Rectangle playable, double yLine) {
        FontMetrics metrics = g.getFontMetrics(PLAYER_FONT);

        double x = playable.getCenterX() - metrics.stringWidth(message) / 2.0;

        g.setFont(PLAYER_FONT);
        g.setColor(FONT_COLOR);
        g.drawString(message, (int) x, (int) yLine - 5);
    }

    private void printMessage(Graphics2D g, String message, Rectangle playable, double yLine) {
        FontMetrics metrics = g.getFontMetrics(MESSAGE_FONT);

        double x = playable.getCenterX() - metrics.stringWidth(message) / 2.0;

        g.setFont(MESSAGE_FONT);
        g.setColor(MESSAGE_COLOR);
        g.drawString(message, (int) x, (int) yLine + metrics.getHeight());
    }
}
