package labb4.game.ui.painters;

import labb4.game.Config;
import labb4.game.Player;
import labb4.game.Table;
import labb4.game.Vector2D;

import java.awt.*;

public class TablePainter {
    private static final Font PLAYER_FONT = new Font("Ubuntu", Font.BOLD, 24);
    private static final Color FONT_COLOR = new Color(10, 134, 0);

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
        
        drawCurrentPlayer(g, table, playableBounds, yLine);
    }

    private void drawCurrentPlayer(Graphics2D g, Table table, Rectangle playable, double yLine) {
        Player player = table.getCurrentPlayer();

        String text = player.name + "'s turn!";
        FontMetrics metrics = g.getFontMetrics(PLAYER_FONT);

        double x = playable.getCenterX() - metrics.stringWidth(text) / 2.0;

        g.setFont(PLAYER_FONT);
        g.setColor(FONT_COLOR);
        g.drawString(text, (int) x, (int) yLine - 5);
    }
}
