import java.awt.*;

public class ClockPainter {
    private static final Color DISABLED_COLOR = new Color(230, 230, 230);

    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color BORDER_COLOR = Color.BLACK;
    private static final Color HOUR_LINES_COLOR = new Color(220, 220, 220);

    private static final Color HOUR_HAND_COLOR = Color.BLACK;
    private static final Color MINUTE_HAND_COLOR = new Color(30, 30, 30);
    private static final Color SECOND_HAND_COLOR = new Color(196, 102, 113);

    public void draw(Graphics2D g, AnalogClock clock) {
        // Background
        g.setColor(BACKGROUND_COLOR);
        g.fillOval(clock.bounds.x, clock.bounds.y, clock.bounds.width, clock.bounds.height);

        // Border
        g.setStroke(new BasicStroke(1));
        g.setColor(BORDER_COLOR);
        g.drawOval(clock.bounds.x, clock.bounds.y, clock.bounds.width, clock.bounds.height);

        // Hour lines
        g.setColor(HOUR_LINES_COLOR);
        for (int angle = 0; angle < 360; angle += 30)
            drawHourLine(g, clock, Math.toRadians(angle));

        // Hour hand
        drawHand(g, clock, 4, clock.isEnabled() ? HOUR_HAND_COLOR : DISABLED_COLOR, 0.7, clock.getHourHandAngle());

        // Minute hand
        drawHand(g, clock, 4, clock.isEnabled() ? MINUTE_HAND_COLOR : DISABLED_COLOR, 0.95, clock.getMinuteHandAngle());

        // Second hand
        drawHand(g, clock, 1, clock.isEnabled() ? SECOND_HAND_COLOR : DISABLED_COLOR, 0.9, clock.getSecondHandAngle());
    }

    private void drawHourLine(Graphics2D g, AnalogClock clock, double angle) {
        double x1 = clock.x + 0.8 * clock.radius * Math.cos(angle - Math.PI/2);
        double y1 = clock.y + 0.8 * clock.radius * Math.sin(angle - Math.PI/2);

        double x2 = clock.x + 0.99 * clock.radius * Math.cos(angle - Math.PI/2);
        double y2 = clock.y + 0.99 * clock.radius * Math.sin(angle - Math.PI/2);

        g.setStroke(new BasicStroke(1));
        g.setColor(HOUR_LINES_COLOR);
        g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }

    private void drawHand(Graphics2D g, AnalogClock clock, int strokeWidth, Color color, double length, double angle) {
        double x2 = clock.x + length * clock.radius * Math.cos(angle - Math.PI/2);
        double y2 = clock.y + length * clock.radius * Math.sin(angle - Math.PI / 2);

        g.setColor(color);
        g.setStroke(new BasicStroke(strokeWidth));
        g.drawLine(clock.x, clock.y, (int) x2, (int) y2);
    }
}
