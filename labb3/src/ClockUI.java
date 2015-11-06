import javax.swing.*;

public class ClockUI extends JFrame {
    private JPanel clockPanel;

    public ClockUI() {
        super("Labb 3: Klocka");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);

        clockPanel = new ClockPanel();

        add(clockPanel);
    }
}
