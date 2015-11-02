import java.awt.*;

public class AnalogClock {
    public static final double DEG_PER_HOUR = 360 / 12.0;
    public static final double DEG_PER_MINUTE = 360 / 60.0;
    public static final double DEG_PER_SECOND = 360 / 60.0;

    public final int x;
    public final int y;
    public final int radius;
    public final Rectangle bounds;

    private ClockPainter painter;

    private int hour;
    private int minute;
    private int second;

    private Integer hourDegree;
    private Integer minuteDegree;

    private boolean enabled;

    public AnalogClock(int x, int y, int radius, ClockPainter painter) {
        this.x = x;
        this.y = y;
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
        painter.draw(g, this);
    }

    public void updateDigital(Integer hourDegree, Integer minuteDegree, int second) {
        this.second = second;

        enabled = !(hourDegree == -1 || minuteDegree == -1);

        this.hourDegree = hourDegree;
        this.minuteDegree = minuteDegree;
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
