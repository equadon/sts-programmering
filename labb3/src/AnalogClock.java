import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class AnalogClock {
    private int xRadius;
    private int yRadius;
    private int x;
    private int y;

    private Rectangle bounds;

    private Calendar calendar;

    public AnalogClock() {
        calendar = Calendar.getInstance();
    }

    public int getXRadius() {
        return xRadius;
    }

    public int getYRadius() {
        return yRadius;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getHour() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        return calendar.get(Calendar.MINUTE);
    }

    public int getSecond() {
        return calendar.get(Calendar.SECOND);
    }

    public double calcHourAngle() {
        return getHour() * 30 + getMinute() * 0.5 + getSecond() * (0.5 / 60);
    }

    public double calcMinuteAngle() {
        return getMinute() * 6 + getSecond() * 0.1;
    }

    public double calcSecondAngle() {
        return getSecond() * 6;
    }

    public void update() {
        calendar.setTime(new Date());
    }

    public void updateSize(int xRadius, int yRadius, int x, int y) {
        this.xRadius = xRadius;
        this.yRadius = yRadius;
        this.x = x;
        this.y = y;

        bounds = new Rectangle(x - xRadius, y - yRadius, 2 * xRadius, 2 * yRadius);
    }
}
