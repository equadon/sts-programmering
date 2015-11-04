package clock;

import clock.painters.ClockPainter;
import clock.painters.DisabledClockPainter;

import java.awt.*;

public class AnalogClock {
    public static final int RADIUS = 50;

    public static final double DEG_PER_HOUR = 360 / 12.0;
    public static final double DEG_PER_MINUTE = 360 / 60.0;
    public static final double DEG_PER_SECOND = 360 / 60.0;

    public static final boolean PERFECT_DIGITAL_ANGLES = false;

    public static final ClockPainter DISABLED_PAINTER = new DisabledClockPainter();

    public final int x;
    public final int y;
    public final int radius;
    public final Rectangle bounds;

    private final ClockPainter painter;

    private int hour;
    private int minute;
    private int second;

    private Integer hourDegree;
    private Integer minuteDegree;

    private boolean enabled;

    public AnalogClock(int row, int col, int radius, int xOffset, ClockPainter painter) {
        this.x = xOffset + radius + col * radius * 2;
        this.y = radius + row * radius * 2;
        this.radius = radius;
        this.painter = painter;

        bounds = new Rectangle(x - radius, y - radius, radius * 2, radius * 2);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public double getHourHandAngle() {
        if (hourDegree != null)
            return Math.toRadians(hourDegree);

        double degPerMin = DEG_PER_HOUR / 60.0;

        return Math.toRadians(hour * DEG_PER_HOUR + minute * degPerMin + second * (degPerMin / 60));
    }

    public double getMinuteHandAngle() {
        if (minuteDegree != null)
            return Math.toRadians(minuteDegree);

        return Math.toRadians(minute * DEG_PER_MINUTE);
    }

    public double getSecondHandAngle() {
        return Math.toRadians(second * DEG_PER_SECOND);
    }

    public void draw(Graphics2D g) {
        if (isEnabled())
            painter.draw(g, this);
        else
            DISABLED_PAINTER.draw(g, this);
    }

    public void updateDigital(Integer hourDegree, Integer minuteDegree, int second) {
        this.second = second;

        enabled = !(hourDegree == -1 || minuteDegree == -1);

        if (PERFECT_DIGITAL_ANGLES) {
            this.hourDegree = hourDegree;
            this.minuteDegree = minuteDegree;
        } else {
            this.hour = hourDegree / 30;
            this.minute = minuteDegree / 6;
        }
    }

    public void update(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;

        hourDegree = null;
        minuteDegree = null;

        enabled = true;
    }
}
