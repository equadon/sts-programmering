package labb4.painters;

import labb4.Config;
import labb4.Hole;
import labb4.PoolTable;
import labb4.TablePanel;

import java.awt.*;

public class TablePainter {
    public void draw(Graphics2D g, TablePanel panel) {
        PoolTable table = panel.getTable();

        Rectangle bounds = table.getBounds();
        Rectangle inner = table.getInnerBounds();
        Rectangle playable = table.getPlayableBounds();

        g.setColor(Config.BORDER_COLOR);
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);

        g.setColor(Config.INNER_BORDER_COLOR);
        g.fillRect(inner.x, inner.y, inner.width, inner.height);

        g.setColor(Config.TABLE_COLOR);
        g.fillRect(playable.x, playable.y, playable.width, playable.height);

        drawHoles(g, panel);

        //g.setColor(Config.INNER_BORDER_COLOR);
        //drawInnerBorder(g, inner, (int) Config.RESIZE_FACTOR * panel.getTable().innerBorderSize);
    }

    private void drawInnerBorder(Graphics2D g, Rectangle inner, int innerBorderWidth) {
        int holeRadius = Config.HOLE_RADIUS;
        int x = (int) inner.getX() + holeRadius;
        int maxX = (int) inner.getMaxX() - holeRadius;
        int y = (int) inner.getY();
        int maxY = y + innerBorderWidth;

        int offset = (int) (1.5 * innerBorderWidth);

        g.fillPolygon(
                new int[] {x, maxX, maxX - offset, x + offset},
                new int[] {y, y, maxY, maxY},
                4
        );

        y = (int) inner.getMaxY() - innerBorderWidth;
        maxY = (int) inner.getMaxY();
        g.fillPolygon(
                new int[] {x + offset, maxX - offset, maxX, x},
                new int[] {y, y, maxY, maxY},
                4
        );

        x = (int) inner.getX();
        maxX = x + innerBorderWidth;
        y = (int) inner.getY() + holeRadius;
        maxY = (int) inner.getMaxY() - holeRadius;

        g.fillPolygon(
                new int[] {x, maxX, maxX, x},
                new int[] {y, y + offset, maxY - offset, maxY},
                4
        );

        x = (int) inner.getMaxX() - innerBorderWidth;
        maxX = x + innerBorderWidth;

        g.fillPolygon(
                new int[] {x, maxX, maxX, x},
                new int[] {y + offset, y, maxY, maxY - offset},
                4
        );
    }

    private void drawHoles(Graphics2D g, TablePanel panel) {
        PoolTable table = panel.getTable();

        Rectangle bounds = table.getPlayableBounds();
        Hole[] holes = table.getHoles();

        g.setColor(Config.HOLE_COLOR);

        int border = panel.getTable().borderSize;

        for (Hole hole : holes) {
            double x = (1 - hole.col) * bounds.getX() + bounds.getMaxX() * hole.col - hole.radius;
            double y = hole.radius + hole.row * (bounds.height / 2.0);

            g.fillOval((int) x, (int) y, 2 * hole.radius, 2 * hole.radius);
        }
    }
}
