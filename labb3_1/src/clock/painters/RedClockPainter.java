package clock.painters;

import clock.AnalogClock;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RedClockPainter extends DefaultClockPainter {
    public static final Font font = new Font("Arial", Font.BOLD, 16);

    public RedClockPainter() {
        backgroundColor = new Color(255, 100, 100);

        secondHandColor = new Color(62, 72, 194);
    }

    @Override
    public void draw(Graphics2D g, AnalogClock clock) {
        super.draw(g, clock);

        DateFormat df = new SimpleDateFormat("d/MM");
        String date = df.format(new Date());

        FontMetrics metrics = g.getFontMetrics(font);
        int width = metrics.stringWidth(date);

        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(date, clock.x - (int) (width / 2.0), clock.y + metrics.getHeight());
    }
}
