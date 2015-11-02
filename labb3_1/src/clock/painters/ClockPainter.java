package clock.painters;

import clock.AnalogClock;

import java.awt.*;

public interface ClockPainter {
    void draw(Graphics2D g, AnalogClock clock);
}
