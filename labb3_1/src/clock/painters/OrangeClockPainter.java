package clock.painters;

import clock.AnalogClock;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrangeClockPainter extends DefaultClockPainter {
    public OrangeClockPainter() {
        backgroundColor = new Color(255, 183, 100);
    }

    @Override
    public void draw(Graphics2D g, AnalogClock clock) {
        super.draw(g, clock);

        DateFormat df = new SimpleDateFormat("YYYY");
        String date = df.format(new Date());

        FontMetrics metrics = g.getFontMetrics(RedClockPainter.font);
        int width = metrics.stringWidth(date);

        g.setColor(Color.BLACK);
        g.setFont(RedClockPainter.font);
        g.drawString(date, clock.x - (int) (width / 2.0), clock.y + metrics.getHeight());
    }
}
