package clock.painters;

import java.awt.*;

public class DisabledClockPainter extends DefaultClockPainter {
    public DisabledClockPainter() {
        hourHandColor = new Color(230, 230, 230);
        minuteHandColor = new Color(230, 230, 230);
        secondHandColor = new Color(230, 230, 230);

        enableHourHand = false;
        enableMinuteHand = false;
        enableSecondHand = false;
    }
}
