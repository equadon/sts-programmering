package tow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Buttons extends JPanel {

    private Game game;

    Buttons(Game g) {
        game = g;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        addHelpButton();
        addSizeButton(1.2, "   Large size   ");
        addSizeButton(1.0, "Normal size");
        addSizeButton(0.8, "Small size");
        addSizeButton(0.6, "Tiny size");
        addStartButton();
        addRandomButton();
        addGoField();
    }

    private void addHelpButton() {

        final String helpText = "Control the car by holding down arrow keys. \n" +
                "Hold down spacebar to brake.\n" +
                "No need to hit keys repeatedly, just hold them down.\n\n" +
                "Place the trailer on red coloured spots to win.\n" +
                "You have limited time and the car can sustain only a few crashes.\n" +
                "To skip to a level you have already visited, just type the name of that level.\n" +
                "Random road means randomly placed obstacles.";

        JButton helpButton = new JButton("Help");
        add(Box.createRigidArea(new Dimension(0, 50)));
        helpButton.setBackground(Color.YELLOW);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(game, helpText);
                game.refocus();
            }
        });
        add(helpButton);
        add(Box.createRigidArea(new Dimension(0, 100)));
    }

    private void addSizeButton(final double scale, String buttonText) {
        JButton sizeButton = new JButton(buttonText);
        sizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.launchSize(scale);
            }
        });
        add(sizeButton);
    }

    private void addStartButton() {

        JButton startButton = new JButton("Restart this level");
        add(Box.createRigidArea(new Dimension(0, 50)));
        startButton.setBackground(Color.GREEN);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.relaunch();
            }
        });
        add(startButton);
    }

    private void addRandomButton() {

        JButton randomButton = new JButton("Random road");
        add(Box.createRigidArea(new Dimension(0, 50)));
        randomButton.setBackground(Color.ORANGE);
        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.launchRandom();
            }
        });
        add(randomButton);
    }

    private void addGoField() {

        final JTextField goField = new JTextField(12);
        JLabel goLabel = new JLabel("Go directly to level:");

        add(Box.createRigidArea(new Dimension(0, 50)));
        JPanel goPanel = new JPanel();
        goPanel.setLayout(new BoxLayout(goPanel, BoxLayout.Y_AXIS));

        goPanel.add(goLabel);
        goField.setMaximumSize(new Dimension(Integer.MAX_VALUE, goField.getPreferredSize().height));
        goPanel.add(goField);
        goField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.tryToGo(goField.getText());
            }
        });
        add(goPanel);
    }
}
