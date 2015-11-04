package clock.painters;

public class DisabledClockPainter extends DefaultClockPainter {
    public DisabledClockPainter() {
        enableHourHand = false;
        enableMinuteHand = false;
        enableSecondHand = false;
    }
}
