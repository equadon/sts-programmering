import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Scout extends HeavyPiece {
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;

    Scout(boolean w, boolean my, Board.Position pos, Board board) {
        super(w, my, pos, board);
    }

    @Override
    Area appearance(Board.Position pos) {
        Area app = new Area(new Ellipse2D.Float(pos.x-WIDTH/2,
                pos.y-WIDTH/2+WIDTH/4,
                WIDTH,
                WIDTH));
            app.add(new Area(new Ellipse2D.Float(pos.x-WIDTH/2,
                    pos.y-WIDTH/2-WIDTH/4,
                    WIDTH,
                    WIDTH)));
            return app;
    }

    @Override
    protected void drawOutline(Graphics page, Board.Position pos, Color color)
    {
        super.drawOutline(page, pos, color);
        page.drawOval(pos.x-WIDTH/2,
                pos.y-WIDTH/2+WIDTH/4,
                WIDTH,
                WIDTH);
    }
}
