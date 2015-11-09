import javax.swing.*;

public class Labb3 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClockUI().setVisible(true);
            }
        });
    }
}
