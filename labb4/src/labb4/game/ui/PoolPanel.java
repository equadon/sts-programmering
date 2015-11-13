package labb4.game.ui;

import labb4.game.GameType;
import labb4.game.Table;
import labb4.game.TableFactory;

import javax.swing.*;
import java.awt.*;

public class PoolPanel extends JPanel {
    private Table table;
    private GameType gameType;

    public PoolPanel() {
        setPreferredSize(new Dimension(600, 400));

        gameType = GameType.EightBall;
        table = TableFactory.createStandardPoolTable(gameType);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        table.draw(g);
    }
}
