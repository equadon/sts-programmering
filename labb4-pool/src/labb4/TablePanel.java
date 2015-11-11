package labb4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TablePanel extends JPanel implements ActionListener {
    private final Timer timer;

    private PoolTable table;

    public TablePanel() {
        timer = new Timer((int) (1000.0 / Config.FPS), this);

        setupTable();

        setPreferredSize(new Dimension(
                table.width,
                table.height
        ));
    }

    public PoolTable getTable() {
        return table;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        table.draw(g, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        table.update();
    }

    private void setupTable() {
        table = PoolTableFactory.createTable(GameType.EIGHT_BALL);
    }
}
