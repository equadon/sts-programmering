import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClockPanel extends JPanel implements ActionListener {
    private Timer timer;
    private AnalogClock clock;

    public ClockPanel() {
        timer = new Timer(50, this);
        clock = new AnalogClock();

        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Rectangle bounds = getBounds();

        int xRadius = bounds.width / 2;
        int yRadius = bounds.height / 2;
        int x = bounds.width / 2;
        int y = bounds.height / 2;

        clock.updateSize(xRadius, yRadius, x, y);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        drawClock(g2d, clock);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        clock.update();
        repaint();
    }

    private void drawClock(Graphics2D g, AnalogClock clock) {
        //g.setColor(Color.WHITE);
        //g.fillOval(clock.getBounds().x, clock.getBounds().y, clock.getBounds().width, clock.getBounds().height);

        g.setColor(Color.BLACK);
        //g.drawOval(clock.getBounds().x, clock.getBounds().y, clock.getBounds().width, clock.getBounds().height);

        for (int angle = 0; angle < 360; angle += 30)
            drawHourLine(g, clock, angle);

        drawHand(g, clock, 0.5, 4, clock.calcHourAngle());
        drawHand(g, clock, 0.65, 3, clock.calcMinuteAngle());
        drawHand(g, clock, 0.7, 2, clock.calcSecondAngle());
        // string fixed with and center content
    }

    private void drawHourLine(Graphics2D g, AnalogClock clock, double degAngle) {
        double angle = Math.toRadians(degAngle - 90);

        double x1 = clock.getX() + 0.8 * clock.getXRadius() * Math.cos(angle);
        double y1 = clock.getY() + 0.8 * clock.getYRadius() * Math.sin(angle);

        double x2 = clock.getX() + 0.95 * clock.getXRadius() * Math.cos(angle);
        double y2 = clock.getY() + 0.95 * clock.getYRadius() * Math.sin(angle);

        g.setStroke(new BasicStroke(2));
        g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);

        drawHourMark(g, clock, degAngle, angle);
    }

    private void drawHourMark(Graphics2D g, AnalogClock clock, double degAngle, double angle) {
        Integer mark = (int) (degAngle / 30);
        Font font = new Font("Arial", Font.PLAIN, 14);
        FontMetrics metrics = g.getFontMetrics(font);

        float xPos = (float) (clock.getX() + 0.75 * clock.getXRadius() * Math.cos(angle));
        float yPos = (float) (clock.getY() + 0.75 * clock.getYRadius() * Math.sin(angle));

        g.drawString((mark == 0) ? "12" : mark.toString(), xPos, yPos);
    }

    private void drawHand(Graphics2D g, AnalogClock clock, double length, int width, double angle) {
        angle = Math.toRadians(angle - 90);

        double x2 = clock.getX() + length * clock.getXRadius() * Math.cos(angle);
        double y2 = clock.getY() + length * clock.getYRadius() * Math.sin(angle);

        g.setStroke(new BasicStroke(width));
        g.drawLine(clock.getX(), clock.getY(), (int) x2, (int) y2);
    }
}
