package clock.painters;

import clock.AnalogClock;

import java.awt.*;

public abstract class ClockPainter {
    protected Color disabledColor = new Color(230, 230, 230);

    protected Color backgroundColor = Color.WHITE;
    protected Color borderColor = Color.BLACK;
    protected Color hourLinesColor = new Color(220, 220, 220);

    protected Color hourHandColor = Color.BLACK;
    protected Color minuteHandColor = new Color(30, 30, 30);
    protected Color secondHandColor = new Color(196, 102, 113);

    protected double hourHandLength = 0.7;
    protected double minuteHandLength = 0.95;
    protected double secondHandLength = 0.9;

    protected int borderStroke = 1;
    protected int hourHandStroke = 4;
    protected int minuteHandStroke = 4;
    protected int secondHandStroke = 1;

    protected double hourLineLength = 0.18;

    public void draw(Graphics2D g, AnalogClock clock) {
        // Background
        g.setColor(backgroundColor);
        g.fillOval(clock.bounds.x, clock.bounds.y, clock.bounds.width, clock.bounds.height);

        // Border
        g.setStroke(new BasicStroke(borderStroke));
        g.setColor(borderColor);
        g.drawOval(clock.bounds.x, clock.bounds.y, clock.bounds.width, clock.bounds.height);

        // Hour lines
        g.setColor(hourLinesColor);
        for (int angle = 0; angle < 360; angle += 30)
            drawHourLine(g, clock, Math.toRadians(angle));

        // Hour hand
        drawHand(g, clock, hourHandStroke, clock.isEnabled() ? hourHandColor : disabledColor, hourHandLength, clock.getHourHandAngle());

        // Minute hand
        drawHand(g, clock, minuteHandStroke, clock.isEnabled() ? minuteHandColor : disabledColor, minuteHandLength, clock.getMinuteHandAngle());

        // Second hand
        drawHand(g, clock, secondHandStroke, clock.isEnabled() ? secondHandColor : disabledColor, secondHandLength, clock.getSecondHandAngle());
    }

    private void drawHourLine(Graphics2D g, AnalogClock clock, double angle) {
        double x1 = clock.x + (0.99 - hourLineLength) * clock.radius * Math.cos(angle - Math.PI/2);
        double y1 = clock.y + (0.99 - hourLineLength) * clock.radius * Math.sin(angle - Math.PI/2);

        double x2 = clock.x + 0.99 * clock.radius * Math.cos(angle - Math.PI/2);
        double y2 = clock.y + 0.99 * clock.radius * Math.sin(angle - Math.PI/2);

        g.setStroke(new BasicStroke(1));
        g.setColor(hourLinesColor);
        g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }

    private void drawHand(Graphics2D g, AnalogClock clock, int strokeWidth, Color color, double length, double angle) {
        double x2 = clock.x + length * clock.radius * Math.cos(angle - Math.PI/2);
        double y2 = clock.y + length * clock.radius * Math.sin(angle - Math.PI/2);

        g.setColor(color);
        g.setStroke(new BasicStroke(strokeWidth));
        g.drawLine(clock.x, clock.y, (int) x2, (int) y2);
    }
}
