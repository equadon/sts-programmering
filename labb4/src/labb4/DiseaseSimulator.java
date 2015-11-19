package labb4;

import javax.swing.*;
import java.awt.*;

/**
 * Uppgift 2, labb 4: Karin Melin och Niklas Persson
 */
public class DiseaseSimulator {
    final static int UPDATE_FREQUENCY = 100;    // Global constant: fps, ie times per second to simulate

    public static void main(String[] args) {
        JFrame frame = new JFrame("Molecules!");

        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MoleculesPanel moleculesPanel = new MoleculesPanel();
        frame.getContentPane().add(moleculesPanel, BorderLayout.CENTER);

        JPanel rightPanel = new RightPanel(moleculesPanel);
        frame.getContentPane().add(rightPanel, BorderLayout.EAST);

        frame.pack();
        frame.setVisible(true);
    }
}
