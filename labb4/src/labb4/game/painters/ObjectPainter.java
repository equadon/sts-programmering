package labb4.game.painters;

import labb4.game.objects.GameObject;

import java.awt.*;

public abstract class ObjectPainter {
    public abstract void draw(Graphics2D g, GameObject object);
}
