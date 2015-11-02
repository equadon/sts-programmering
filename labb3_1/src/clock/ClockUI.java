package clock;

import javax.swing.*;

public class ClockUI extends JFrame {
    private JPanel clockPanel;

    public ClockUI() {
        super("Labb 3: Dual Clock");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        clockPanel = new ClockPanel();
        add(clockPanel);

        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClockUI().setVisible(true));
    }
}
