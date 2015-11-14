package labb4.game.ui.painters;

import labb4.game.Config;
import labb4.game.objects.GameObject;

import java.awt.*;

public class HolePainter extends ObjectPainter {
    @Override
    public void draw(Graphics2D g, GameObject object) {
        Rectangle.Double bounds = object.getBounds();

        g.setColor(Config.DEFAULT_HOLE_COLOR);
        g.fillOval((int) bounds.x, (int) bounds.y, (int) bounds.width, (int) bounds.height);
    }
}
