import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class DualClock {
    public static int[][][] NUMBERS = new int[][][] {
            {{90,180}, {270,180}, {0,180}, {0,180}, {0,90}, {0,270}},
            {{-1,-1}, {225,180}, {-1,-1}, {0,180}, {-1,-1}, {0,0}},
            {{90,90}, {270,180}, {90,180}, {0,270}, {0,90}, {270,270}},
            {{90,90}, {270,180}, {90,90}, {270,180}, {90,90}, {0,270}},
            {{180,180}, {180,180}, {0,90}, {0,180}, {-1,-1}, {0,0}},
            {{90,180}, {270,270}, {0,90}, {270,180}, {90,90}, {0,270}},
            {{90,180}, {270,270}, {0,180}, {270,180}, {0,90}, {0,270}},
            {{90,90}, {270,180}, {-1,-1}, {0,180}, {-1,-1}, {0,0}},
            {{90,180}, {270,180}, {90,180}, {270,180}, {0,90}, {0,270}},
            {{90,180}, {270,180}, {0,90}, {0,180}, {-1,-1}, {0,0}},
    };

    public enum Mode { ANALOG, DIGITAL }

    public static final int RADIUS = 30;
    public static final int COLS = 2;
    public static final int ROWS = 3;

    private Mode mode;
    private Calendar calendar;

    private AnalogClock[] hourClocks;
    private AnalogClock[] minuteClocks;
    private AnalogClock[] secondClocks;

    public DualClock() {
        mode = Mode.ANALOG;
        calendar = Calendar.getInstance();

        hourClocks = new AnalogClock[2 * COLS * ROWS];
        minuteClocks = new AnalogClock[2 * COLS * ROWS];
        secondClocks = new AnalogClock[2 * COLS * ROWS];

        ClockPainter painter = new ClockPainter();

        // Initiate clocks
        for (int i = 0; i < COLS*ROWS; i++) {
            int row = i / COLS;
            int col = i % COLS;

            int minutesOffset = 9 * RADIUS;
            int secondsOffset = 18 * RADIUS;

            hourClocks[i] = new AnalogClock(RADIUS + col * RADIUS * 2, RADIUS + row * RADIUS * 2, RADIUS, painter);
            minuteClocks[i] = new AnalogClock(minutesOffset + RADIUS + col * RADIUS * 2, RADIUS + row * RADIUS * 2, RADIUS, painter);
            secondClocks[i] = new AnalogClock(secondsOffset + RADIUS + col * RADIUS * 2, RADIUS + row * RADIUS * 2, RADIUS, painter);
        }

        for (int i = COLS*ROWS; i < 2*COLS*ROWS; i++) {
            int row = (i / COLS) % ROWS;
            int col = (i % COLS) % ROWS;

            int minutesOffset = 9 * RADIUS;
            int secondsOffset = 18 * RADIUS;

            hourClocks[i] = new AnalogClock(4*RADIUS + RADIUS + col * RADIUS * 2, RADIUS + row * RADIUS * 2, RADIUS, painter);
            minuteClocks[i] = new AnalogClock(4*RADIUS + minutesOffset + RADIUS + col * RADIUS * 2, RADIUS + row * RADIUS * 2, RADIUS, painter);
            secondClocks[i] = new AnalogClock(4*RADIUS + secondsOffset + RADIUS + col * RADIUS * 2, RADIUS + row * RADIUS * 2, RADIUS, painter);
        }
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

    public void switchMode() {
        mode = (mode == Mode.ANALOG) ? Mode.DIGITAL : Mode.ANALOG;
    }

    public void draw(Graphics2D g) {
        for (AnalogClock clock : hourClocks)
            clock.draw(g);

        for (AnalogClock clock : minuteClocks)
            clock.draw(g);

        for (AnalogClock clock : secondClocks)
            clock.draw(g);
    }

    public void update() {
        calendar.setTime(new Date());

        if (mode == Mode.ANALOG)
            updateAnalog();
        else
            updateDigital();
    }

    private void updateAnalog() {
        for (AnalogClock clock : hourClocks)
            clock.update(getHour(), getMinute(), getSecond());

        for (AnalogClock clock : minuteClocks)
            clock.update(getHour(), getMinute(), getSecond());

        for (AnalogClock clock : secondClocks)
            clock.update(getHour(), getMinute(), getSecond());
    }

    private void updateDigital() {
        updateHours();
        updateMinutes();
        updateSeconds();
    }

    private void updateHours() {
        updateDigital(String.format("%02d", getHour()), hourClocks);
    }

    private void updateMinutes() {
        updateDigital(String.format("%02d", getMinute()), minuteClocks);
    }

    private void updateSeconds() {
        updateDigital(String.format("%02d", getSecond()), secondClocks);
    }

    private void updateDigital(String string, AnalogClock[] clocks) {
        for (int i = 0; i < 12; i++) {
            int part = Integer.parseInt(string.substring(i / 6, (i / 6) + 1));
            int[] degrees = NUMBERS[part][i % 6];

            clocks[i].updateDigital(degrees[0], degrees[1], getSecond());
        }
    }
}
