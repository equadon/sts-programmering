package labb4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RightPanel extends JPanel implements ActionListener {
    private static final int PANEL_WIDTH = 200;
    private static final int LABEL_HEIGHT = 30;

    private final MoleculesPanel moleculesPanel;
    private final JButton button;
    private final JLabel deadLabel;

    public RightPanel(MoleculesPanel moleculesPanel) {
        this.moleculesPanel = moleculesPanel;

        setPreferredSize(new Dimension(PANEL_WIDTH, moleculesPanel.HEIGHT));

        button = new JButton("Pause");
        button.addActionListener(this);
        add(button);

        deadLabel = new JLabel("Dead: 0");
        deadLabel.setVisible(false);
        deadLabel.setPreferredSize(new Dimension(PANEL_WIDTH, LABEL_HEIGHT));
        deadLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(deadLabel);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (button.getText().equals("Pause")) {
            moleculesPanel.pause();
            deadLabel.setVisible(true);
            deadLabel.setText("Dead: " + moleculesPanel.countDead());
            button.setText("Start");
        } else {
            moleculesPanel.start();
            deadLabel.setVisible(false);
            button.setText("Pause");
        }
    }
}
