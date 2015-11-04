package clock;

import javax.swing.*;

public class Labb3 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClockUI().setVisible(true));
    }
}
