import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ClockPanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private DualClock dualClock;

    public ClockPanel() {
        timer = new Timer(50, this);
        timer.start();

        dualClock = new DualClock();

        int width = 13 * 2 * DualClock.RADIUS;
        int height = DualClock.ROWS * 2 * DualClock.RADIUS;

        setPreferredSize(new Dimension(width, height));

        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        dualClock.draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dualClock.update();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        dualClock.switchMode();
    }
}
