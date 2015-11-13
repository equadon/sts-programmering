package labb4.game.painters;

import labb4.game.Config;
import labb4.game.Table;

import java.awt.*;

public class TablePainter {
    public void draw(Graphics2D g, Table table) {
        Rectangle bounds = table.getBounds();
        Rectangle playableBounds = table.getPlayableBounds();

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
    }
}
