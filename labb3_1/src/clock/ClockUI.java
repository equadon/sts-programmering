package clock;

import javax.swing.*;

public class ClockUI extends JFrame {
    private JPanel clockPanel;

    public ClockUI() {
        super("Labb 3: Analog/Digital Clock");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        clockPanel = new ClockPanel();
        add(clockPanel);

        pack();
    }
}
