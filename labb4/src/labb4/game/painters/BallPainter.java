package labb4.game.painters;

import labb4.game.Config;
import labb4.game.Vector2D;
import labb4.game.interfaces.Aimable;
import labb4.game.objects.GameObject;

import java.awt.*;

public class BallPainter extends ObjectPainter {
    @Override
    public void draw(Graphics2D g, GameObject object) {
        Rectangle.Double bounds = object.getBounds();

        g.setColor(Config.BALL_BORDER_COLOR);
        g.fillOval((int) bounds.x, (int) bounds.y, (int) bounds.width, (int) bounds.height);

        g.setColor(object.getColor());
        g.fillOval(
                (int) bounds.x + Config.BALL_BORDER_SIZE,
                (int) bounds.y + Config.BALL_BORDER_SIZE,
                (int) bounds.width - 2 * Config.BALL_BORDER_SIZE,
                (int) bounds.height - 2 * Config.BALL_BORDER_SIZE
        );

        if (object instanceof Aimable) {
            drawAim(g, object);
        }
    }

    private void drawAim(Graphics2D g, GameObject object) {
        Aimable aimable = (Aimable) object;

        if (aimable.isAiming()) {
            Vector2D objPosition = object.getPosition();
            Vector2D aimPosition = aimable.getAimPosition();

            g.setColor(Color.BLACK);
            g.drawLine((int) objPosition.x, (int) objPosition.y, (int) aimPosition.x, (int) aimPosition.y);
        }
    }
}
