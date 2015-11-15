package labb4.game.ui.painters;

import labb4.game.Config;
import labb4.game.objects.GameObject;

import java.awt.*;

public class PocketPainter extends ObjectPainter {
    @Override
    public void draw(Graphics2D g, GameObject object) {
        Rectangle.Double bounds = object.getBounds();

        if (Config.DISPLAY_BOUNDING_BOXES) {
            g.setColor(Config.BALL_BLACK_COLOR);
            g.setStroke(new BasicStroke(Config.BALL_BORDER_SIZE));
            g.drawRect((int) Math.round(bounds.x), (int) Math.round(bounds.y), (int) Math.round(bounds.width), (int) Math.round(bounds.height));
        }

        g.setColor(Config.DEFAULT_HOLE_COLOR);
        g.fillOval((int) bounds.x, (int) bounds.y, (int) bounds.width, (int) bounds.height);
    }
}
