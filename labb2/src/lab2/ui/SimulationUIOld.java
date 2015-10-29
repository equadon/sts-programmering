package lab2.ui;

import javax.swing.*;
import java.awt.*;

public class SimulationUIOld extends JFrame {
    private JLabel dayLabel;
    private JLabel sickLabel;
    private JLabel deadLabel;

    private SimulationPanel simulationPanel;

    public SimulationUIOld() throws HeadlessException {
        super("Disease Simulation");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Simulation panel
        simulationPanel = new SimulationPanel();
        add(simulationPanel, BorderLayout.CENTER);

        // Info panel
        JPanel infoPanel = new JPanel(new FlowLayout());
        dayLabel = new JLabel("Day: 0");
        sickLabel = new JLabel("Sick: 0");
        deadLabel = new JLabel("Dead: 0");
        infoPanel.add(dayLabel);
        infoPanel.add(sickLabel);
        infoPanel.add(deadLabel);
        add(infoPanel, BorderLayout.SOUTH);

        // Control panel
        JPanel controlPanel = new JPanel(new GridLayout(0, 2));
        controlPanel.add(new JLabel("Population:"));
        controlPanel.add(new JTextField("0"));
        add(controlPanel, BorderLayout.EAST);

        pack();
        setVisible(true);
    }

    private void createUIComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(1, 1));
        simulationPanel = new SimulationPanel();

        mainPanel.add(simulationPanel);
    }
}
