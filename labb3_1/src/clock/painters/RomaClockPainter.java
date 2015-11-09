package clock.painters;

import clock.AnalogClock;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

public class RomaClockPainter extends DefaultClockPainter {
    private Image football;
    private Image logo;

    public RomaClockPainter() {
        try {
            football = ImageIO.read(new File("images/fotboll2.png"));
            logo = ImageIO.read(new File("images/roma_logo2.png"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), e.getClass().toString(), JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        enableBackground = false;

        secondHandLength = 0.7;
        secondHandStroke = 4;
        secondHandColor = Color.GREEN;
    }

    @Override
    public void draw(Graphics2D g, AnalogClock clock) {
        g.setColor(backgroundColor);
        g.fillOval(clock.bounds.x, clock.bounds.y, clock.bounds.width, clock.bounds.height);

        // Background image
        int width = clock.bounds.width;
        int offset = (int) (width * 0.1);

        g.drawImage(logo, clock.bounds.x + offset, clock.bounds.y, width - 2*offset, clock.bounds.height, null, null);

        super.draw(g, clock);

        drawFootball(g, clock);
    }

    private void drawFootball(Graphics2D g, AnalogClock clock) {
        double angle = clock.getSecondHandAngle();

        double x2 = clock.x + 0.75 * clock.radius * Math.cos(angle - Math.PI/2);
        double y2 = clock.y + 0.75 * clock.radius * Math.sin(angle - Math.PI/2);

        int radius = (int) (clock.bounds.width / 8.0);

        g.drawImage(football, (int) (x2 - radius/2.0), (int) (y2 - radius/2.0), 2*radius, 2*radius, null, null);
    }
}
