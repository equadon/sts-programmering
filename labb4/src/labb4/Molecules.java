package labb4;

import javax.swing.*;

public class Molecules {
    final static int UPDATE_FREQUENCY = 100;    // Global constant: fps, ie times per second to simulate

    public static void main(String[] args) {

        JFrame frame = new JFrame("Molecules!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MoleculesPanel panel = new MoleculesPanel();

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
